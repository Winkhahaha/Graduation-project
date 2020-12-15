

package org.mineok.aspect;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.mineok.common.annotation.SysLog;
import org.mineok.common.utils.HttpContextUtils;
import org.mineok.common.utils.IPUtils;
import org.mineok.entity.SysLogInfo;
import org.mineok.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;


/**
 * 系统日志，切面处理类
 *
 * @author Mark sunlightcs@gmail.com
 */
@Aspect
@Component
public class SysLogAspect {
    @Resource
    private SysLogService sysLogService;

    @Pointcut("@annotation(org.mineok.common.annotation.SysLog)")
    public void logPointCut() {

    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = point.proceed();
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;

        //保存日志
        saveSysLog(point, time);

        return result;
    }

    private void saveSysLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        SysLogInfo logInfo = new SysLogInfo();
        SysLog syslog = method.getAnnotation(SysLog.class);
        if (syslog != null) {
            // 注解上的描述
            logInfo.setOperation(syslog.value());
        }
        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        logInfo.setMethod(className + "." + methodName + "()");
        // 获取请求的参数
        Object[] args = joinPoint.getArgs();
        // 参数转为JSON串
        String params = new Gson().toJson(args);
        // 设置参数串
        logInfo.setParams(params);
        // 获取当前登录用户名
        String userName = null;
        if (args[0] instanceof Map) {
            // 部分用户名存在于@RequestParm(Map)中
            Map map = (Map) args[0];
            userName = map.get("userName").toString();
        } else {
            userName = "用户名记录在参数中！";
        }
        //获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        // 设置IP地址
        logInfo.setIp(IPUtils.getIpAddr(request));
        // 设置用户名
        logInfo.setUsername(userName);
        logInfo.setTime(time);
        logInfo.setCreateDate(new Date());
        //保存系统日志
        sysLogService.save(logInfo);
    }
}

package org.mineok.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.mineok.common.utils.PageUtils;
import org.mineok.common.utils.R;
import org.mineok.entity.DbZdjs;

import java.util.Map;

/**
 * @author GaoMing
 * @email mineok@foxmail.com
 * @date 2020-12-31 14:41:35
 */
public interface DbZdjsService extends IService<DbZdjs> {

    PageUtils queryPage(Map<String, Object> params);

    /*
    指导教师获取待答辩学生列表
     */
    R ZDJS_Student_List(Integer defenceStatus,String tid);

    /*
       指导教师填写评分表
     */
    R addDB_ZDJS(DbZdjs zdjs);

    /*
        根据课题id获取指导教师评阅
     */
    R getDB_ZDJSByTopicId(Integer topicId);

    /*
     学生获取指导教师的答辩意见
     */
    R getZDJS_Score(String stuId);

    /*
     学生申请答辩线上/线下
     */
    R stuSetDefenceStatus(Integer defenceStatus, String StuId);

    // 申请之前
    R stuSetDefenceStatusBefore(String stuId);

    // 学生取消答辩申请
    R stuCancelDefenceStatus(String stuId);

    // 取消之前
    R stuCancelDefenceStatusBefore(String stuId);
}


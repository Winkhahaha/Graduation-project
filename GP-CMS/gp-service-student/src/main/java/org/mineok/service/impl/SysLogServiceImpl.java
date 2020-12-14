

package org.mineok.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.mineok.common.utils.PageUtils;
import org.mineok.common.utils.Query;
import org.mineok.dao.SysLogDao;
import org.mineok.entity.SysLogInfo;
import org.mineok.service.SysLogService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("sysLogService")
public class SysLogServiceImpl extends ServiceImpl<SysLogDao, SysLogInfo> implements SysLogService {


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key = params.get("key").toString();
        IPage<SysLogInfo> page = this.page(
                new Query<SysLogInfo>().getPage(params),
                new QueryWrapper<SysLogInfo>().like(StringUtils.isNotBlank(key), "username", key)
                .or().like(StringUtils.isNotBlank(key), "operation", key));
        return new PageUtils(page);
    }
}

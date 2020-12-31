package org.mineok.service.impl;

import org.mineok.dao.DbOtherDao;
import org.mineok.entity.DbOther;
import org.mineok.service.DbOtherService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.mineok.common.utils.PageUtils;
import org.mineok.common.utils.Query;



@Service("dbOtherService")
public class DbOtherServiceImpl extends ServiceImpl<DbOtherDao, DbOther> implements DbOtherService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<DbOther> page = this.page(
                new Query<DbOther>().getPage(params),
                new QueryWrapper<DbOther>()
        );

        return new PageUtils(page);
    }

}
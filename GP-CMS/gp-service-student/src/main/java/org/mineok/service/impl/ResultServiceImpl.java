package org.mineok.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.mineok.common.utils.PageUtils;
import org.mineok.common.utils.Query;
import org.mineok.dao.ResultDao;
import org.mineok.entity.Result;
import org.mineok.service.ResultService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("resultService")
public class ResultServiceImpl extends ServiceImpl<ResultDao, Result> implements ResultService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<Result> page = this.page(
                new Query<Result>().getPage(params),
                new QueryWrapper<Result>()
        );

        return new PageUtils(page);
    }

}
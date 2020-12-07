package org.mineok.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.mineok.common.utils.PageUtils;
import org.mineok.common.utils.Query;
import org.mineok.dao.ReportDao;
import org.mineok.entity.Report;
import org.mineok.service.ReportService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("reportService")
public class ReportServiceImpl extends ServiceImpl<ReportDao, Report> implements ReportService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<Report> page = this.page(
                new Query<Report>().getPage(params),
                new QueryWrapper<Report>()
        );

        return new PageUtils(page);
    }

}
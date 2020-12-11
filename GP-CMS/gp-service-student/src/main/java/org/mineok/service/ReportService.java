package org.mineok.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.mineok.common.utils.PageUtils;
import org.mineok.common.utils.R;
import org.mineok.entity.Report;

import java.util.Map;

/**
 * 
 * 开题报告
 * @author GaoMing
 * @email mineok@foxmail.com
 * @date 2020-12-07 14:30:27
 */
public interface ReportService extends IService<Report> {

    PageUtils queryPage(Map<String, Object> params);

    R getStuReport(String stuId);

    // 添加开题报告前判断
    R saveReportBefore(String stuId);

    // 更新开题报告前判断
    R updateReportBefore(Integer reportId);

    // 删除前判断
    R deleteReportBefore(Integer reportId);

    // 删除开题报告
    R deleteReport(Integer reportId);
}


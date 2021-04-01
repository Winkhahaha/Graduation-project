package org.mineok.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.mineok.common.utils.PageUtils;
import org.mineok.common.utils.R;
import org.mineok.entity.Report;

import java.util.Map;

/**
 * 开题报告
 *
 * @author GaoMing
 * @email mineok@foxmail.com
 * @date 2020-12-07 14:30:27
 */
public interface ReportService extends IService<Report> {

    PageUtils queryPage(Map<String, Object> params);

    // 获取当前学生的开题报告
    R getStuReport(String stuId);

    R getReportInfo(Integer reportId);

    // 添加开题报告前判断
    R saveReportBefore(String stuId);

    // 添加开题报告
    R saveReport(Report report, String stuId);

    // 更新开题报告前判断
    R updateReportBefore(Integer reportId);

    // 更新开题报告
    R updateReport(Report report);

    // 删除前判断
    R deleteReportBefore(Integer reportId);

    // 删除开题报告
    R deleteReport(Integer reportId);

    // 学生:提交开题报告审批
    R submitReportApproval(Integer reportId);

    // 学生:提交开题报告审批之前
    R beforeSubmitReportApproval(Integer reportId);

    // 学生:取消审批
    R cancelReportApproval(Integer reportId);

    // 学生:取消审批之前
    R beforeCancelReportApproval(Integer reportId);

    // 教师:得到所指导学生的报告列表
    R getStuReportList(Integer appStatus, String tid);

    // 教师:设置开题报告的审批意见
    R addOpinions(String opinions, Integer reportId);

    R addReportScore(Integer reportScore, Integer reportId);

    // 教师:确认报告审批
    R commitApproval(Integer reportId);

    // 教师:驳回报告审批
    R rejectApproval(Integer reportId);
}


package org.mineok.controller;

import org.mineok.common.utils.PageUtils;
import org.mineok.common.utils.R;
import org.mineok.entity.Report;
import org.mineok.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * @author GaoMing
 * @email mineok@foxmail.com
 * @date 2020-12-07 14:30:27
 */
@RestController
@RequestMapping("/report")
public class ReportController {
    @Autowired
    private ReportService reportService;

    /*
        报告详情
     */
    @RequestMapping("/info/{reportInfo}")
    public R getStuReport(@PathVariable("reportInfo") Integer reportInfo) {
        return reportService.getReportInfo(reportInfo);
    }

    /*
        学生开题报告列表:Vo展示数据
     */
    @RequestMapping("/stu/{stuId}")
    public R getStuReport(@PathVariable("stuId") String stuId) {
        return reportService.getStuReport(stuId);
    }

    @RequestMapping("/save/before/{stuId}")
    public R saveReportBefore(@PathVariable("stuId") String stuId) {
        return reportService.saveReportBefore(stuId);
    }

    @RequestMapping("/save/{stuId}")
    public R saveReport(@RequestBody Report report, @PathVariable("stuId") String stuId) {
        return reportService.saveReport(report, stuId);
    }

    @RequestMapping("/update/before/{reportId}")
    public R updateReportBefore(@PathVariable("reportId") Integer reportId) {
        return reportService.updateReportBefore(reportId);
    }

    @RequestMapping("/update")
    public R updateReport(@RequestBody Report report) {
        return reportService.updateReport(report);
    }

    @RequestMapping("/delete/before/{reportId}")
    public R deleteBefore(@PathVariable("reportId") Integer reportId) {
        return reportService.deleteReportBefore(reportId);
    }

    @RequestMapping("/delete/{reportId}")
    public R deleteReport(@PathVariable("reportId") Integer reportId) {
        return reportService.deleteReport(reportId);
    }

    /**
     * 学生:提交/取消报告审批
     */
    @RequestMapping("/stu/approval/submit/before/{reportId}")
    public R submitApprovalBefore(@PathVariable("reportId") Integer reportId) {
        return reportService.beforeSubmitReportApproval(reportId);
    }

    @RequestMapping("/stu/approval/submit/{reportId}")
    public R submitApproval(@PathVariable("reportId") Integer reportId) {
        return reportService.submitReportApproval(reportId);
    }

    @RequestMapping("/stu/approval/cancel/before/{reportId}")
    public R cancelApprovalBefore(@PathVariable("reportId") Integer reportId) {
        return reportService.beforeCancelReportApproval(reportId);
    }

    @RequestMapping("/stu/approval/cancel/{reportId}")
    public R cancelApproval(@PathVariable("reportId") Integer reportId) {
        return reportService.cancelReportApproval(reportId);
    }

    /**
     * 教师:查看报告列表/审批/驳回/添加审批意见
     */
    @RequestMapping("/teacher/approval/list/{tid}")
    public R approvalList(@RequestParam("appStatus") Integer appStatus, @PathVariable("tid") String tid) {
        return reportService.getStuReportList(appStatus, tid);
    }

    @RequestMapping("/teacher/add/opinions/{reportId}")
    public R addopinions(@RequestParam("opinions") String opinions, @PathVariable("reportId") Integer reportId) {
        return reportService.addOpinions(opinions, reportId);
    }

    @RequestMapping("/teacher/commit/approval/{reportId}")
    public R commitapproval(@PathVariable("reportId") Integer reportId) {
        return reportService.commitApproval(reportId);
    }

    @RequestMapping("/teacher/reject/approval/{reportId}")
    public R rejectapproval(@PathVariable("reportId") Integer reportId) {
        return reportService.rejectApproval(reportId);
    }
}

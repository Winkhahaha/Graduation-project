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

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = reportService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
//    @RequestMapping("/info/{id}")
//    public R info(@PathVariable("id") Integer id) {
//        Report report = reportService.getById(id);
//        return R.ok().put("report", report);
//    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody Report report) {
        reportService.save(report);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody Report report) {
        reportService.updateById(report);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids) {
        reportService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

    @RequestMapping("/info/{stuId}")
    public R getStuReport(@PathVariable("stuId") String stuId) {
        return reportService.getStuReport(stuId);
    }

    @RequestMapping("/save/before/{stuId}")
    public R saveReportBefore(@PathVariable("stuId") String stuId) {
        return reportService.saveReportBefore(stuId);
    }

    @RequestMapping("/update/before/{reportId}")
    public R updateReportBefore(@PathVariable("reportId") Integer reportId) {
        return reportService.updateReportBefore(reportId);
    }

    @RequestMapping("/delete/before/{reportId}")
    public R deleteBefore(@PathVariable("reportId") Integer reportId) {
        return reportService.deleteReportBefore(reportId);
    }

    @RequestMapping("/delete/{reportId}")
    public R deleteReport(@PathVariable("reportId") Integer reportId) {
        return reportService.deleteReport(reportId);
    }

}

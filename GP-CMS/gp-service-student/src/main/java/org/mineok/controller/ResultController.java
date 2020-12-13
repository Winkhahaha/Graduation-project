package org.mineok.controller;

import org.mineok.common.utils.PageUtils;
import org.mineok.common.utils.R;
import org.mineok.entity.Result;
import org.mineok.service.ResultService;
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
@RequestMapping("/result")
public class ResultController {

    @Autowired
    private ResultService resultService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = resultService.queryPage(params);
        return R.ok().put("page", page);
    }


//    /**
//     * 信息
//     */
//    @RequestMapping("/info/{id}")
//    public R info(@PathVariable("id") Integer id) {
//        Result result = resultService.getById(id);
//        return R.ok().put("result", result);
//    }

//    /**
//     * 保存
//     */
//    @RequestMapping("/save")
//    public R save(@RequestBody Result result) {
//        resultService.save(result);
//        return R.ok();
//    }

//    /**
//     * 修改
//     */
//    @RequestMapping("/update")
//    public R update(@RequestBody Result result) {
//        resultService.updateById(result);
//        return R.ok();
//    }
//
//    /**
//     * 删除
//     */
//    @RequestMapping("/delete")
//    public R delete(@RequestBody Integer[] ids) {
//        resultService.removeByIds(Arrays.asList(ids));
//        return R.ok();
//    }

    /*
        毕设详情
     */
    @RequestMapping("/info/{resultInfo}")
    public R getStuResult(@PathVariable("resultInfo") Integer resultInfo) {
        return resultService.getResultInfo(resultInfo);
    }

    /*
        学生毕设列表:Vo展示数据
     */
    @RequestMapping("/stu/{stuId}")
    public R getStuResult(@PathVariable("stuId") String stuId) {
        return resultService.getStuResult(stuId);
    }

    @RequestMapping("/save/before/{stuId}")
    public R saveResultBefore(@PathVariable("stuId") String stuId) {
        return resultService.saveResultBefore(stuId);
    }

    @RequestMapping("/save/{stuId}")
    public R saveResult(@RequestBody Result result, @PathVariable("stuId") String stuId) {
        return resultService.saveResult(result, stuId);
    }

    @RequestMapping("/update/before/{resultId}")
    public R updateResultBefore(@PathVariable("resultId") Integer resultId) {
        return resultService.updateResultBefore(resultId);
    }

    @RequestMapping("/update")
    public R updateResult(@RequestBody Result result) {
        return resultService.updateResult(result);
    }

    @RequestMapping("/delete/before/{resultId}")
    public R deleteBefore(@PathVariable("resultId") Integer resultId) {
        return resultService.deleteResultBefore(resultId);
    }

    @RequestMapping("/delete/{resultId}")
    public R deleteResult(@PathVariable("resultId") Integer resultId) {
        return resultService.deleteResult(resultId);
    }

    /**
     * 学生:提交/取消报告审批
     */
    @RequestMapping("/stu/approval/submit/before/{resultId}")
    public R submitApprovalBefore(@PathVariable("resultId") Integer resultId) {
        return resultService.beforeSubmitResultApproval(resultId);
    }

    @RequestMapping("/stu/approval/submit/{resultId}")
    public R submitApproval(@PathVariable("resultId") Integer resultId) {
        return resultService.submitResultApproval(resultId);
    }

    @RequestMapping("/stu/approval/cancel/before/{resultId}")
    public R cancelApprovalBefore(@PathVariable("resultId") Integer resultId) {
        return resultService.beforeCancelResultApproval(resultId);
    }

    @RequestMapping("/stu/approval/cancel/{resultId}")
    public R cancelApproval(@PathVariable("resultId") Integer resultId) {
        return resultService.cancelResultApproval(resultId);
    }

    /**
     * 教师:查看报告列表/审批/驳回/添加审批意见
     */
    @RequestMapping("/teacher/approval/list/{tid}")
    public R approvalList(@RequestParam("appStatus") Integer appStatus, @PathVariable("tid") String tid) {
        return resultService.getStuResultList(appStatus, tid);
    }

    @RequestMapping("/teacher/add/opinions/{resultId}")
    public R addopinions(@RequestParam("opinions") String opinions, @PathVariable("resultId") Integer resultId) {
        return resultService.addOpinions(opinions, resultId);
    }

    @RequestMapping("/teacher/commit/approval/{resultId}")
    public R commitApproval(@PathVariable("resultId") Integer resultId) {
        return resultService.commitApproval(resultId);
    }

    @RequestMapping("/teacher/reject/approval/{resultId}")
    public R rejectApproval(@PathVariable("resultId") Integer resultId) {
        return resultService.rejectApproval(resultId);
    }

    /**
     * 院系负责人:终审
     */
    @RequestMapping("/director/approval/list/{directorId}")
    public R rejectapproval(@RequestParam("appStatus") Integer appStatus, @PathVariable("directorId") String directorId) {
        return resultService.getFinalApprovalList(appStatus, directorId);
    }

    // 确认终审
    @RequestMapping("/director/commit/approval/{resultId}")
    public R commitFinalApproval(@PathVariable("resultId") Integer resultId) {
        return resultService.commitFinalApproval(resultId);
    }

    // 驳回终审
    @RequestMapping("/director/reject/approval/{resultId}")
    public R rejectFinalApproval(@PathVariable("resultId") Integer resultId) {
        return resultService.rejectFinalApproval(resultId);
    }

    // 获取所有通过终审的毕设成果
    @RequestMapping("/stu/final/list")
    public R approvalList(@RequestParam Map<String, Object> params) {
        return resultService.queryResultsByApproval(params);
    }
}

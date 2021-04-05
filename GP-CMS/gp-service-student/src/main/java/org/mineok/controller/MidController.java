package org.mineok.controller;

import org.mineok.common.utils.R;
import org.mineok.entity.Mid;
import org.mineok.service.MidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author GaoMing
 * @email mineok@foxmail.com
 * @date 2020-12-07 14:30:27
 */
@RestController
@RequestMapping("/mid")
public class MidController {
    @Autowired
    private MidService midService;

    /*
        中期资料详情
     */
    @RequestMapping("/info/{midId}")
    public R getStuMid(@PathVariable("midId") Integer midId) {
        return midService.getMidInfo(midId);
    }

    /*
        学生中期资料列表:Vo展示数据
     */
    @RequestMapping("/stu/{stuId}")
    public R getStuMid(@PathVariable("stuId") String stuId) {
        return midService.getStuMid(stuId);
    }

    @RequestMapping("/save/before/{stuId}")
    public R saveMidBefore(@PathVariable("stuId") String stuId) {
        return midService.saveMidBefore(stuId);
    }

    @RequestMapping("/save/{stuId}")
    public R saveMid(@RequestBody Mid mid, @PathVariable("stuId") String stuId) {
        return midService.saveMid(mid, stuId);
    }

    @RequestMapping("/update/before/{midId}")
    public R updateMidBefore(@PathVariable("midId") Integer midId) {
        return midService.updateMidBefore(midId);
    }

    @RequestMapping("/update")
    public R updateMid(@RequestBody Mid mid) {
        return midService.updateMid(mid);
    }

    @RequestMapping("/delete/before/{midId}")
    public R deleteBefore(@PathVariable("midId") Integer midId) {
        return midService.deleteMidBefore(midId);
    }

    @RequestMapping("/delete/{midId}")
    public R deleteMid(@PathVariable("midId") Integer midId) {
        return midService.deleteMid(midId);
    }

    /**
     * 学生:提交/取消中期资料审批
     */
    @RequestMapping("/stu/approval/submit/before/{midId}")
    public R submitApprovalBefore(@PathVariable("midId") Integer midId) {
        return midService.beforeSubmitMidApproval(midId);
    }

    @RequestMapping("/stu/approval/submit/{midId}")
    public R submitApproval(@PathVariable("midId") Integer midId) {
        return midService.submitMidApproval(midId);
    }

    @RequestMapping("/stu/approval/cancel/before/{midId}")
    public R cancelApprovalBefore(@PathVariable("midId") Integer midId) {
        return midService.beforeCancelMidApproval(midId);
    }

    @RequestMapping("/stu/approval/cancel/{midId}")
    public R cancelApproval(@PathVariable("midId") Integer midId) {
        return midService.cancelMidApproval(midId);
    }

    /**
     * 教师:查看中期资料列表/审批/驳回/添加审批意见
     */
    @RequestMapping("/teacher/approval/list/{tid}")
    public R approvalList(@RequestParam("appStatus") Integer appStatus, @PathVariable("tid") String tid) {
        return midService.getStuMidList(appStatus, tid);
    }

    @RequestMapping("/teacher/add/opinions/{midId}")
    public R addopinions(@RequestParam("opinions") String opinions, @PathVariable("midId") Integer midId) {
        return midService.addOpinions(opinions, midId);
    }

    @RequestMapping("/teacher/add/score/{midId}")
    public R addMidScore(@RequestParam("midScore") Integer midScore, @PathVariable("midId") Integer midId) {
        return midService.addMidScore(midScore, midId);
    }

    @RequestMapping("/teacher/commit/approval/{midId}")
    public R commitapproval(@PathVariable("midId") Integer midId) {
        return midService.commitApproval(midId);
    }

    @RequestMapping("/teacher/reject/approval/{midId}")
    public R rejectapproval(@PathVariable("midId") Integer midId) {
        return midService.rejectApproval(midId);
    }
}

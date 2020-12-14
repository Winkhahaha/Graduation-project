package org.mineok.controller;

import java.util.Arrays;
import java.util.Map;

import org.mineok.entity.Topic;
import org.mineok.service.TeacherService;
import org.mineok.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.mineok.entity.Teacher;
import org.mineok.service.TeacherService;
import org.mineok.common.utils.PageUtils;
import org.mineok.common.utils.R;


/**
 * @author GaoMing
 * @email mineok@foxmail.com
 * @date 2020-12-07 14:30:27
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private TopicService topicService;

    /**
     * 当前老师待反选题目列表
     */
    @RequestMapping("/invert/list/{tid}")
    public R invertList(@PathVariable("tid") String tid) {
        return topicService.getInvertTopicsByTeacherId(tid);
    }

    /**
     * 反选
     */
    @RequestMapping("/invert/{stuId}/{topicId}")
    public R invertList(@PathVariable("stuId") String stuId, @PathVariable("topicId") Integer topicId) {
        return teacherService.invertStu(stuId, topicId);
    }

    /**
     * 驳回学生申请
     */
    @RequestMapping("/reject/{stuId}")
    public R rejectList(@PathVariable("stuId") String stuId) {
        return teacherService.rejectStu(stuId);
    }


    /**
     * 根据工号(登录用户名)查询老师的信息
     */
    @RequestMapping("/info/{tid}")
    public R info(@PathVariable("tid") String tid) {
        return teacherService.teacherInfo(tid);
    }


    /**
     * 根据当前教师的最大选题数进行课题新增
     */
    @RequestMapping("/topic/save")
    public R saveByTopicCount(@RequestBody Topic topic) {
        return teacherService.saveByTopicCount(topic);
    }

    /**
     * 新增前判断
     *
     * @param tid
     * @return
     */
    @RequestMapping("/topic/save/before/{tid}")
    public R saveBefore(@PathVariable("tid") String tid) {
        return teacherService.saveBefore(tid);
    }


    /**
     * 根据当前课题审批状态进行修改
     */
    @RequestMapping("/topic/update")
    public R updateByStatus(@RequestBody Topic topic) {
        return teacherService.updateByStatus(topic);
    }

    @RequestMapping("/topic/update/before/{topicId}")
    public R updateBefore(@PathVariable("topicId") Integer topicId) {
        return teacherService.updateBefore(topicId);
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids) {
        teacherService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

    /**
     * 提交审批
     *
     * @param tid
     * @param topicId
     * @return
     */
    @RequestMapping("/approval/submit/{tid}/{topicId}")
    public R submitApproval(@PathVariable("tid") String tid, @PathVariable("topicId") Integer topicId) {
        return teacherService.submitApproval(tid, topicId);
    }

    @RequestMapping("/approval/submit/before/{tid}/{topicId}")
    public R submitApprovalBefore(@PathVariable("tid") String tid, @PathVariable("topicId") Integer topicId) {
        return teacherService.submitApprovalBefore(tid, topicId);
    }

    /**
     * 取消审批
     *
     * @param topicId
     * @return
     */
    @RequestMapping("/approval/cancel/{topicId}")
    public R cancelApproval(@PathVariable("topicId") Integer topicId) {
        return teacherService.cancelApproval(topicId);
    }

    @RequestMapping("/approval/cancel/before/{topicId}")
    public R cancelApprovalBefore(@PathVariable("topicId") Integer topicId) {
        return teacherService.cancelApprovalBefore(topicId);
    }

    /**
     * 删除
     */
    @RequestMapping("/topic/delete/{tid}")
    public R deleteByTopicIds(@RequestBody Integer[] ids, @PathVariable("tid") String tid) {
        return teacherService.deleteNotApproval(ids,tid);
    }

    /**
     * 删除前判断
     */
    @RequestMapping("/topic/delete/before")
    public R deleteBefore(@RequestBody Integer[] ids) {
        return teacherService.deleteBefore(ids);
    }

}

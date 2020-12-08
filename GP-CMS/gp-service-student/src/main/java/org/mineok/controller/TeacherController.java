package org.mineok.controller;

import java.util.Arrays;
import java.util.Map;

import org.mineok.service.TeacherService;
import org.mineok.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
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
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions(":teacher:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = teacherService.queryPage(params);

        return R.ok().put("page", page);
    }

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
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id) {
        Teacher teacher = teacherService.getById(id);
        return R.ok().put("teacher", teacher);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody Teacher teacher) {
        teacherService.save(teacher);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody Teacher teacher) {
        teacherService.updateById(teacher);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids) {
        teacherService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}

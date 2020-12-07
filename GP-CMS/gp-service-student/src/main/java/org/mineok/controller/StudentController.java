package org.mineok.controller;

import org.mineok.common.utils.PageUtils;
import org.mineok.common.utils.R;
import org.mineok.entity.Student;
import org.mineok.entity.Topic;
import org.mineok.service.StudentService;
import org.mineok.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;


/**
 * @author G
 * @email mineok@foxmail.com
 * @date 2020-11-25 20:14:11
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private TopicService topicService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = studentService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
//    @RequiresPermissions("test:student:info")
    public R info(@PathVariable("id") Integer id) {
        Student student = studentService.getById(id);

        return R.ok().put("student", student);
    }

    /*
        获取登录用户(学生/教师)的信息
     */
    @RequestMapping("/sys/info/{stuId}")
    public R infoByStuId(@PathVariable("stuId") String stuId) {
        Student student = studentService.findByStuId(stuId);
        return R.ok().put("student", Collections.singletonList(student));
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
//    @RequiresPermissions("test:student:save")
    public R save(@RequestBody Student student) {
        studentService.save(student);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
//    @RequiresPermissions("test:student:update")
    public R update(@RequestBody Student student) {
        studentService.updateById(student);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
//    @RequiresPermissions("test:student:delete")
    public R delete(@RequestBody Integer[] ids) {
        studentService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

    @RequestMapping("/chose/topic/{stuId}/{topicId}")
    public R choseTopic(@PathVariable("stuId") String stuId, @PathVariable("topicId") Integer topicId) {
        return studentService.choseTopic(stuId, topicId);
    }


}

package org.mineok.controller;

import org.mineok.common.utils.PageUtils;
import org.mineok.common.utils.R;
import org.mineok.entity.Student;
import org.mineok.entity.Topic;
import org.mineok.service.StudentService;
import org.mineok.service.TopicService;
import org.mineok.vo.TopicVo;
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

    /*
        获取登录用户(学生)的信息
     */
    @RequestMapping("/sys/info/{stuId}")
    public R infoByStuId(@PathVariable("stuId") String stuId) {
        Student student = studentService.findByStuId(stuId);
        return R.ok().put("student", Collections.singletonList(student));
    }


    @RequestMapping("/chose/topic/{stuId}/{topicId}")
    public R choseTopic(@PathVariable("stuId") String stuId, @PathVariable("topicId") Integer topicId) {
        return studentService.choseTopic(stuId, topicId);
    }

    @RequestMapping("/cancel/topic/{stuId}/{topicId}")
    public R cancelTopic(@PathVariable("stuId") String stuId, @PathVariable("topicId") Integer topicId) {
        return studentService.cancelTopic(stuId, topicId);
    }


    @RequestMapping("/myTopic/{stuId}")
    public R myTopic(@PathVariable("stuId") String stuId) {
        return studentService.myTopic(stuId);
    }


}

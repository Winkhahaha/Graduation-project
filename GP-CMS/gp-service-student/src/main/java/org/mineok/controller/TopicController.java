package org.mineok.controller;

import java.util.Arrays;
import java.util.Map;

import org.mineok.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.mineok.entity.Topic;
import org.mineok.service.TopicService;
import org.mineok.common.utils.PageUtils;
import org.mineok.common.utils.R;


/**
 * @author GaoMing
 * @email mineok@foxmail.com
 * @date 2020-12-07 14:30:27
 */
@RestController
@RequestMapping("/topic")
public class TopicController {

    @Autowired
    private TopicService topicService;

    /**
     * 当前老师所管理的课题列表
     * @param tid
     * @return
     */
    @RequestMapping("/teacher/list/{tid}")
    public R listByTeacher(@PathVariable("tid") String tid) {
        return topicService.getTopicByTeacherId(tid);
    }

    /**
     * 根据学生的院系展现对应的选题
     * @param params
     * @param stuId
     * @return
     */
    @RequestMapping("/chose/list/{stuId}")
    public R topicsList(@RequestParam Map<String, Object> params, @PathVariable("stuId") String stuId) {
        return topicService.getTopics(params, stuId);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{topicId}")
    public R info(@PathVariable("topicId") Integer topicId) {
        return topicService.topicInfo(topicId);
    }

}

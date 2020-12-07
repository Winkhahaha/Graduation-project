package org.mineok.controller;

import java.util.Arrays;
import java.util.Map;

import org.mineok.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
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
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = topicService.queryPage(params);
        return R.ok().put("page", page);
    }

    @RequestMapping("/chose/list")
    public R topicsList(@RequestParam Map<String, Object> params) {
        PageUtils page = topicService.getTopics(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id) {
        Topic topic = topicService.getById(id);
        return R.ok().put("topic", topic);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody Topic topic) {
        topicService.save(topic);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody Topic topic) {
        topicService.updateById(topic);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids) {
        topicService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}

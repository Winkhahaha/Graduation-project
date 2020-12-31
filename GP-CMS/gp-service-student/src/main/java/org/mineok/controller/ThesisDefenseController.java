package org.mineok.controller;

import org.mineok.common.utils.R;
import org.mineok.entity.DbZdjs;
import org.mineok.service.DbOtherService;
import org.mineok.service.DbZdjsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Gaoming
 * @Email mineok@foxmail.com
 * @Date 2020/12/31/ 14:53
 * @Description 答辩
 */
@RestController
@RequestMapping("/thesis")
public class ThesisDefenseController {

    @Autowired
    private DbZdjsService zdjsService;
    @Autowired
    private DbOtherService otherService;

    @RequestMapping("/zdjs/info/{topicId}")
    public R getDB_ZDJSByTopicId(@PathVariable("topicId") Integer topicId) {
        return zdjsService.getDB_ZDJSByTopicId(topicId);
    }

    @RequestMapping("/zdjs/save")
    public R save(@RequestBody DbZdjs zdjs) {
        return zdjsService.addDB_ZDJS(zdjs);
    }

    @RequestMapping("/zdjs/list/{tid}")
    public R ZDJS_Student_List(@PathVariable("tid") String tid) {
        return zdjsService.ZDJS_Student_List(tid);
    }
}

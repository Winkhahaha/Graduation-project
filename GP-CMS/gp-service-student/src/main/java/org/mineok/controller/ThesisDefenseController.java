package org.mineok.controller;

import org.mineok.common.utils.R;
import org.mineok.entity.DbOther;
import org.mineok.entity.DbZdjs;
import org.mineok.service.DbOtherService;
import org.mineok.service.DbZdjsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public R ZDJS_Student_List(@RequestParam("defenceStatus") Integer defenceStatus, @PathVariable("tid") String tid) {
        return zdjsService.ZDJS_Student_List(defenceStatus,tid);
    }

    @RequestMapping("/zdjs/stu/{stuId}")
    public R getZDJS_Score(@PathVariable("stuId") String stuId) {
        return zdjsService.getZDJS_Score(stuId);
    }

    @RequestMapping("/other/list/{tid}")
    public R getOtherTeacherTopicList(@RequestParam("key") String key, @PathVariable("tid") String tid) {
        return otherService.getOtherTeacherTopicList(key, tid);
    }

    @RequestMapping("/other/save")
    public R save(@RequestBody DbOther other) {
        return otherService.addDB_Other(other);
    }

    @RequestMapping("/other/info/{tid}/{topicId}")
    public R getDB_OtherByTopicId(@PathVariable("tid") String tid, @PathVariable("topicId") Integer topicId) {
        return otherService.getDB_OtherBytid(tid, topicId);
    }

    @RequestMapping("/other/stu/{stuId}")
    public R getOther_ScoreList(@PathVariable("stuId") String stuId) {
        return otherService.getOtherScoreList(stuId);
    }

    @RequestMapping("/stu/score/{stuId}")
    public R getFinalScore(@PathVariable("stuId") String stuId) {
        return otherService.getFinalScore(stuId);
    }

    @RequestMapping("/stu/apply/defence/before/{stuId}")
    public R stuSetDefenceStatusBefore(@PathVariable("stuId") String stuId) {
        return zdjsService.stuSetDefenceStatusBefore(stuId);
    }

    @RequestMapping("/stu/apply/defence/{stuId}")
    public R stuSetDefenceStatus(@RequestParam("defenceStatus") Integer defenceStatus, @PathVariable("stuId") String stuId) {
        return zdjsService.stuSetDefenceStatus(defenceStatus, stuId);
    }

    @RequestMapping("/stu/cancel/defence/before/{stuId}")
    public R stuCancelDefenceStatusBefore(@PathVariable("stuId") String stuId) {
        return zdjsService.stuCancelDefenceStatusBefore(stuId);
    }

    @RequestMapping("/stu/cancel/defence/{stuId}")
    public R stuCancelDefenceStatus(@PathVariable("stuId") String stuId) {
        return zdjsService.stuCancelDefenceStatus(stuId);
    }
}

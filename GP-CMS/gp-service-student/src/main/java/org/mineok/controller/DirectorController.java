package org.mineok.controller;

import org.mineok.common.utils.PageUtils;
import org.mineok.common.utils.R;
import org.mineok.entity.Director;
import org.mineok.service.DirectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * @author GaoMing
 * @email mineok@foxmail.com
 * @date 2020-12-09 15:33:56
 */
@RestController
@RequestMapping("/director")
public class DirectorController {
    @Autowired
    private DirectorService directorService;

    @RequestMapping("/info/{directorId}")
    public R info(@PathVariable("directorId") String directorId) {
        return directorService.dirInfoByDirId(directorId);
    }

    /**
     * 获取当前审批列表
     */
    @RequestMapping("/approval/list/{directorId}")
    public R approvalTopicList(@RequestParam Map<String, Object> params, @PathVariable("directorId") String directorId) {
        return directorService.approvalTopicList(params, directorId);
    }

    /**
     * 确认审批课题
     */
    @RequestMapping("/commit/topic/{topicId}")
    public R commitTopic(@PathVariable("topicId") Integer topicId) {
        return directorService.commitApprovalTopic(topicId);
    }

    /**
     * 驳回课题
     */
    @RequestMapping("/reject/topic/{topicId}")
    public R cancelTopic(@PathVariable("topicId") Integer topicId) {
        return directorService.cancelApprovalTopic(topicId);
    }

    /**
     * 获取负责人负责的老师
     */

    @RequestMapping("/teacher/list/{directorId}")
    public R teacherList(@PathVariable("directorId") String directorId) {
        return directorService.teacherList(directorId);
    }

    /**
     * 设置所负责教师的课题数
     */

    @RequestMapping("/set/topicCount/{tid}")
    public R setTopicCount(@RequestParam("topicCount") Integer topicCount, @PathVariable("tid") String tid) {
        return directorService.setTopicCount(topicCount, tid);
    }

    @RequestMapping("/add/opinions/{topicId}")
    public R setTopicCount(@RequestParam("opinions") String opinions, @PathVariable("topicId") Integer topicId) {
        return directorService.addOpinions(opinions, topicId);
    }

}

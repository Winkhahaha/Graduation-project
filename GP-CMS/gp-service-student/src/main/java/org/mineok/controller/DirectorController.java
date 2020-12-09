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

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = directorService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{directorId}")
    public R info(@PathVariable("directorId") String directorId) {
        return directorService.dirInfoByDirId(directorId);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody Director director) {
        directorService.save(director);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody Director director) {
        directorService.updateById(director);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids) {
        directorService.removeByIds(Arrays.asList(ids));
        return R.ok();
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

}

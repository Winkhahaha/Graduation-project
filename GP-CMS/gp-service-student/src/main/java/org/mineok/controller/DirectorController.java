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
@RequestMapping("//director")
public class DirectorController {
    @Autowired
    private DirectorService directorService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("/:director:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = directorService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id) {
        Director director = directorService.getById(id);
        return R.ok().put("director", director);
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

}

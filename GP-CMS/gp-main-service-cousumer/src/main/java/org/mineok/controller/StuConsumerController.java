package org.mineok.controller;

import org.mineok.common.utils.R;
import org.mineok.service.StuFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Gaoming
 * @Email mineok@foxmail.com
 * @Date 2020/12/04/ 16:07
 * @Description
 */
@RestController
@RequestMapping("/order")
public class StuConsumerController {

    @Autowired
    StuFeignService stuFeignService;

    @RequestMapping("/{id}")
    public R findById(@PathVariable("id") Integer id) {
        return R.ok().put("student", stuFeignService.info(id));
    }
}

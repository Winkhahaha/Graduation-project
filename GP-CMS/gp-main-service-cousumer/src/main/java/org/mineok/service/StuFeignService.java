package org.mineok.service;

import org.mineok.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author Gaoming
 * @Email mineok@foxmail.com
 * @Date 2020/12/05/ 21:47
 * @Description
 */
@Component
@FeignClient(value = "gp-test-student-service")
public interface StuFeignService {

    @RequestMapping("test/student/info/{id}")
    public R info(@PathVariable("id") Integer id);
}

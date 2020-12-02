package org.mineok.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.mineok.common.utils.PageUtils;
import org.mineok.entity.StudentEntity;


import java.util.Map;

/**
 * @author G
 * @email mineok@foxmail.com
 * @date 2020-11-25 20:14:11
 */
public interface StudentService extends IService<StudentEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


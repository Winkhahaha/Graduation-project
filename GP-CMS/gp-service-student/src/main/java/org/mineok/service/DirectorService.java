package org.mineok.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.mineok.common.utils.PageUtils;
import org.mineok.entity.Director;

import java.util.Map;

/**
 * 
 *
 * @author GaoMing
 * @email mineok@foxmail.com
 * @date 2020-12-09 15:33:56
 */
public interface DirectorService extends IService<Director> {

    PageUtils queryPage(Map<String, Object> params);
}


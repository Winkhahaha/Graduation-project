package org.mineok.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.mineok.common.utils.PageUtils;
import org.mineok.entity.Result;

import java.util.Map;

/**
 * 
 * 毕设成果
 * @author GaoMing
 * @email mineok@foxmail.com
 * @date 2020-12-07 14:30:27
 */
public interface ResultService extends IService<Result> {

    PageUtils queryPage(Map<String, Object> params);
}


package org.mineok.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.mineok.common.utils.PageUtils;
import org.mineok.entity.DbOther;

import java.util.Map;

/**
 * 
 *
 * @author GaoMing
 * @email mineok@foxmail.com
 * @date 2020-12-31 14:41:34
 */
public interface DbOtherService extends IService<DbOther> {

    PageUtils queryPage(Map<String, Object> params);
}


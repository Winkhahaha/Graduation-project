package org.mineok.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.mineok.common.utils.PageUtils;
import org.mineok.entity.Topic;
import org.mineok.vo.TopicVO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 选题
 * @author GaoMing
 * @email mineok@foxmail.com
 * @date 2020-12-07 14:30:27
 */
public interface TopicService extends IService<Topic> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils getTopics(Map<String, Object> params);
}


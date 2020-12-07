package org.mineok.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.mineok.entity.Topic;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.mineok.vo.TopicV;
import org.mineok.vo.TopicVo;

import java.util.List;

/**
 * 
 * 
 * @author GaoMing
 * @email mineok@foxmail.com
 * @date 2020-12-07 14:30:27
 */
@Mapper
public interface TopicDao extends BaseMapper<Topic> {

    List<TopicVo> topicListCanChose(IPage<TopicVo> page);
	
}

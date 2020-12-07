package org.mineok.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.mineok.entity.Topic;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.mineok.vo.TopicVO;

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

    List<TopicVO> topicListCanChose(IPage<TopicVO> page);
	
}

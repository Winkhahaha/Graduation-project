package org.mineok.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.mineok.entity.Topic;
import org.mineok.vo.InvertTopicVo;
import org.mineok.vo.TopicVo;

import java.util.List;

/**
 * @author GaoMing
 * @email mineok@foxmail.com
 * @date 2020-12-07 14:30:27
 */
@Mapper
public interface TopicDao extends BaseMapper<Topic> {

    IPage<TopicVo> topicListCanChose(IPage<TopicVo> page,
                                     @Param("deptId") Integer deptId,
                                     @Param("key") String key);

    List<InvertTopicVo> invertTopicList(String tid);

}

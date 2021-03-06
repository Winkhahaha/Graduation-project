package org.mineok.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.mineok.common.utils.PageUtils;
import org.mineok.common.utils.R;
import org.mineok.entity.Topic;
import org.mineok.vo.InvertTopicVo;

import java.util.List;
import java.util.Map;

/**
 * 选题
 *
 * @author GaoMing
 * @email mineok@foxmail.com
 * @date 2020-12-07 14:30:27
 */
public interface TopicService extends IService<Topic> {

    PageUtils queryPage(Map<String, Object> params);

    // 学生选题列表
    R getTopics(Map<String, Object> params , String stuId);

    // 教师反选列表
    R getInvertTopicsByTeacherId(String tid);

    // 课题详情
    R topicInfo(Integer topicId);

    R getTopicByTeacherId(String tid);

}


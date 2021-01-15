package org.mineok.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.mineok.common.utils.PageUtils;
import org.mineok.common.utils.R;
import org.mineok.entity.Student;
import org.mineok.vo.TopicVo;

import java.util.Map;

/**
 * 学生
 *
 * @author GaoMing
 * @email mineok@foxmail.com
 * @date 2020-11-25 20:14:11
 */
public interface StudentService extends IService<Student> {

    PageUtils queryPage(Map<String, Object> params);

    Student findByStuId(String stuId);

    // 学生:选题
    R choseTopic(String stuId, Integer topicId);

    // 学生:选题之前
    R choseTopicBefore(String stuId, Integer topicId);

    // 学生:取消选题
    R cancelTopic(String stuId, Integer topicId);

    // 取消选题之前
    R cancelTopicBefore(String stuId, Integer topicId);

    // 学生:我的选题
    R myTopic(String stuId);
}


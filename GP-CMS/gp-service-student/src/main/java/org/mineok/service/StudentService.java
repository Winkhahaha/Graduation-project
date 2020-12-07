package org.mineok.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.mineok.common.utils.PageUtils;
import org.mineok.common.utils.R;
import org.mineok.entity.Student;

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

    R choseTopic(String stuId, Integer topicId);
}


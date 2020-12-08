package org.mineok.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.mineok.common.utils.PageUtils;
import org.mineok.common.utils.R;
import org.mineok.entity.Teacher;

import java.util.Map;

/**
 * 教师
 *
 * @author GaoMing
 * @email mineok@foxmail.com
 * @date 2020-12-07 14:30:27
 */
public interface TeacherService extends IService<Teacher> {

    PageUtils queryPage(Map<String, Object> params);

    // 反选学生
    R invertStu(String stuId, Integer tid);

    // 驳回学生选题
    R rejectStu(String stuId);
}


package org.mineok.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.mineok.common.utils.PageUtils;
import org.mineok.common.utils.R;
import org.mineok.entity.Teacher;
import org.mineok.entity.Topic;

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

    // 获取当前教师个人信息
    R teacherInfo(String tid);

    // 教师提交课题审核
    R submitApproval(String tid, Integer topicId);

    // 审核之前
    R submitApprovalBefore(String tid, Integer topicId);

    // 取消审批
    R cancelApproval(Integer topicId);

    // 取消审批之前
    R cancelApprovalBefore(Integer topicId);

    // 新增课题(判断是否超过开题数)
    R saveByTopicCount(Topic topic);

    // 新增课题之前判断
    R saveBefore(String tid);

    // 修改课题(需要依据课题的审批状态)
    R updateByStatus(Topic topic);

    // 修改课题前的状态判断
    R updateBefore(Integer topicId);

    // 删除没在审批流程中的课题
    R deleteNotApproval(Integer[] ids, String tid);

    // 删除前进行判断
    R deleteBefore(Integer[] ids);
}


package org.mineok.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.http.HttpStatus;
import org.mineok.common.utils.PageUtils;
import org.mineok.common.utils.Query;
import org.mineok.common.utils.R;
import org.mineok.dao.StudentDao;
import org.mineok.dao.TeacherDao;
import org.mineok.dao.TopicDao;
import org.mineok.entity.Student;
import org.mineok.entity.Teacher;
import org.mineok.entity.Topic;
import org.mineok.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Map;


@Service("teacherService")
public class TeacherServiceImpl extends ServiceImpl<TeacherDao, Teacher> implements TeacherService {

    @Resource
    private StudentDao studentDao;
    @Resource
    private TopicDao topicDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<Teacher> page = this.page(
                new Query<Teacher>().getPage(params),
                new QueryWrapper<Teacher>()
        );

        return new PageUtils(page);
    }

    @Override
    public R invertStu(String stuId, Integer tid) {
        Student student = studentDao.selectOne(new QueryWrapper<Student>().eq("stu_id", stuId));
        Topic topic = topicDao.selectById(tid);
        if (student == null || topic == null) {
            return R.error(HttpStatus.SC_NOT_FOUND, "系统异常:参数错误！");
        }
        if (student.getTopicStatus() == 2) {
            return R.error(HttpStatus.SC_BAD_REQUEST, "已反选该学生，请勿重复操作！");
        }
        // 反选
        // 设置学生的选题状态:2即为反选成功
        student.setTopicStatus(2);
        // 将学生学号正式存入选题表中
        topic.setStuId(stuId);
        // 学校规定:一个论文只能对应一个学生
        // 故反选成功后,无论该课题人数是否达到上限,都将该课题状态设为不可选
        topic.setStatus(1);
        studentDao.updateById(student);
        topicDao.updateById(topic);
        return R.ok("反选成功！");
    }

    @Override
    public R rejectStu(String stuId) {
        Student student = studentDao.selectOne(new QueryWrapper<Student>().eq("stu_id", stuId));
//        Topic topic = topicDao.selectById(tid);
        if (student == null) {
            return R.error(HttpStatus.SC_NOT_FOUND, "系统异常:参数错误！");
        }
        if (student.getTopicStatus() == 2) {
            return R.error(HttpStatus.SC_NOT_FOUND, "已反选不可驳回！");
        }
        // 驳回
        // 反选失败状态为-1,学生可在我的选题中看到并且取消掉该选题
        student.setTopicStatus(-1);
//        topic.setSelected(topic.getSelected() - 1);
        studentDao.updateById(student);
//        topicDao.updateById(topic);
        return R.ok("已驳回该学生选题申请！");
    }

    @Override
    public R teacherInfo(String tid) {
        Teacher teacher = baseMapper.selectOne(new QueryWrapper<Teacher>().eq("tid", tid));
        if (teacher == null) {
            return R.error();
        }
        return R.ok().put("teacher", Collections.singletonList(teacher));
    }

    /**
     * 提交审批
     *
     * @param tid
     * @param topicId
     * @return
     */
    @Override
    public R submitApproval(String tid, Integer topicId) {
        Teacher teacher = baseMapper.selectOne(new QueryWrapper<Teacher>().eq("tid", tid));
        Topic topic = topicDao.selectById(topicId);
        if (teacher == null || topic == null) {
            return R.error(HttpStatus.SC_NOT_FOUND, "系统异常:参数错误！");
        }
        if (topic.getApprovalStatus().equals(1)){
            return R.error( "已提交审批，请勿重复操作！");
        }
        if (topic.getApprovalStatus().equals(2)){
            return R.error( "审批已通过，请勿重复操作！");
        }
        if (topic.getApprovalStatus().equals(-1)){
            return R.error( "审批未通过，请先取消该次审批并进行课题修改后再进行提交！");
        }
        // 提交审批:为课题关联审批学院,系统自动发配到审批人的课题列表
        topic.setDeptId(teacher.getDeptId());
        // 设置课题状态为:等待审批
        topic.setApprovalStatus(1);
        topicDao.updateById(topic);
        return R.ok("审批已提交至负责人！");
    }

    @Override
    public R cancelApproval(Integer topicId) {
        Topic topic = topicDao.selectById(topicId);
        if (topic == null) {
            return R.error(HttpStatus.SC_NOT_FOUND, "系统异常:参数错误！");
        }
        if (topic.getApprovalStatus().equals(0)) {
            return R.error("请先提交审批！");
        }
        if (topic.getApprovalStatus().equals(2)) {
            return R.error("该课题已通过审批，无法取消！");
        }
        topic.setApprovalStatus(0);
        topic.setDeptId(0);
        topicDao.updateById(topic);
        return R.ok("取消审批成功！");
    }

}
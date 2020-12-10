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
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
        // 提交审批:为课题关联审批学院,系统自动发配到审批人的课题列表
        topic.setDeptId(teacher.getDeptId());
        // 设置课题状态为:等待审批
        topic.setApprovalStatus(1);
        topicDao.updateById(topic);
        return R.ok("审批已提交至负责人！");
    }

    @Override
    public R submitApprovalBefore(String tid, Integer topicId) {
        Teacher teacher = baseMapper.selectOne(new QueryWrapper<Teacher>().eq("tid", tid));
        Topic topic = topicDao.selectById(topicId);
        if (teacher == null || topic == null) {
            return R.error(HttpStatus.SC_NOT_FOUND, "系统异常:参数错误！");
        }
        if (topic.getApprovalStatus().equals(1)) {
            return R.error("已提交审批，请勿重复操作！");
        }
        if (topic.getApprovalStatus().equals(2)) {
            return R.error("审批已通过，请勿重复操作！");
        }
        if (topic.getApprovalStatus().equals(-1)) {
            return R.error("审批未通过，请先取消该次审批并进行课题修改后再进行提交！");
        }
        return R.ok();
    }

    @Override
    public R cancelApproval(Integer topicId) {
        Topic topic = topicDao.selectById(topicId);
        topic.setApprovalStatus(0);
        topic.setDeptId(0);
        topicDao.updateById(topic);
        return R.ok("取消审批成功！");
    }

    @Override
    public R cancelApprovalBefore(Integer topicId) {
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
        return R.ok();
    }

    @Override
    public R saveByTopicCount(Topic topic) {
        Teacher teacher = baseMapper.selectOne(new QueryWrapper<Teacher>().eq("tid", topic.getTid()));
        // 添加成功,当前课题数加1
        teacher.setCurrentCount(teacher.getCurrentCount() + 1);
        baseMapper.updateById(teacher);
        topicDao.insert(topic);
        return R.ok("添加成功！");
    }

    @Override
    public R saveBefore(String tid) {
        Teacher teacher = baseMapper.selectOne(new QueryWrapper<Teacher>().eq("tid", tid));
        if (ObjectUtils.isEmpty(teacher)) {
            return R.error("系统异常！");
        }
        // 当前已添加的课题数 < 最大数,则可以添加课题
        if (teacher.getCurrentCount() + 1 > teacher.getTopicCount()) {
            return R.error("您的课题数已达上限，不能添加！");
        }
        return R.ok();
    }

    @Override
    public R updateByStatus(Topic topic) {
        if (ObjectUtils.isEmpty(topic)) {
            return R.error("系统异常！");
        }
        topicDao.updateById(topic);
        return R.ok("修改成功！");
    }

    @Override
    public R updateBefore(Integer topicId) {
        Topic topic = topicDao.selectById(topicId);
        if (ObjectUtils.isEmpty(topic)) {
            return R.error("系统异常！");
        }
        // -1审核未通过,1审核中,2审核通过
        if (topic.getApprovalStatus() == -1 || topic.getApprovalStatus() == 1) {
            return R.error("请先取消该次审批！");
        }
        if (topic.getApprovalStatus() == 2) {
            return R.error("已审批成功，暂不能修改！");
        }
        return R.ok();
    }

    @Override
    public R deleteNotApproval(Integer[] ids, String tid) {
        Teacher teacher = baseMapper.selectOne(new QueryWrapper<Teacher>().eq("tid", tid));
        if (ObjectUtils.isEmpty(teacher)) {
            return R.error("系统异常！");
        }
        topicDao.deleteBatchIds(Arrays.asList(ids));
        teacher.setCurrentCount(teacher.getCurrentCount() - 1);
        baseMapper.updateById(teacher);
        return R.ok("删除成功！");
    }

    @Override
    public R deleteBefore(Integer[] ids) {
        List<Topic> topicList = topicDao.selectBatchIds(Arrays.asList(ids));
        if (CollectionUtils.isEmpty(topicList)) {
            return R.error("系统异常！");
        }
        for (Topic topic : topicList) {
            // 如果存在一个topic的审批状态大于0,1审批中,2审批完成,则不能执行此次删除
            if (topic.getApprovalStatus() > 0) {
                return R.error("存在审批流程中的课题，无法执行删除!");
            }
        }
        return R.ok();
    }
}
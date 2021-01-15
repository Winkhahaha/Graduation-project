package org.mineok.service.impl;

import org.apache.http.HttpStatus;
import org.mineok.common.utils.R;
import org.mineok.dao.StudentDao;
import org.mineok.dao.TeacherDao;
import org.mineok.dao.TopicDao;
import org.mineok.entity.Student;
import org.mineok.entity.Teacher;
import org.mineok.entity.Topic;
import org.mineok.service.StudentService;
import org.mineok.vo.TopicVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.mineok.common.utils.PageUtils;
import org.mineok.common.utils.Query;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;


@Service
public class StudentServiceImpl extends ServiceImpl<StudentDao, Student> implements StudentService {

    @Resource
    private StudentDao studentDao;
    @Resource
    private TopicDao topicDao;
    @Resource
    private TeacherDao teacherDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<Student> page = this.page(
                new Query<Student>().getPage(params),
                new QueryWrapper<Student>()
        );

        return new PageUtils(page);
    }

    @Override
    public Student findByStuId(String stuId) {
        QueryWrapper<Student> wrapper = new QueryWrapper<Student>();
        wrapper.eq("stu_id", stuId);
        return baseMapper.selectOne(wrapper);
    }

    /**
     * 选题
     *
     * @param stuId
     * @param topicId
     * @return
     */
    @Override
    public R choseTopic(String stuId, Integer topicId) {
        Student student = this.findByStuId(stuId);
        Topic topic = topicDao.selectById(topicId);
        // 学生选择该题目,等待老师反选
        // 选择后不能再选其他题目,并且等待反选期间也不可选择其他题目
        student.setTopicId(topicId);
        student.setTopicStatus(1);
        // 将已选人数加1
        topic.setSelected(topic.getSelected() + 1);
        // 如果选择后,已选人数达到了限制人数,则将课题状态设置为不可选
        if (topic.getSelected().equals(topic.getToplimit())) {
            topic.setStatus(1);
        }
        studentDao.updateById(student);
        topicDao.updateById(topic);
        return R.ok("选题成功！");
    }

    @Override
    public R choseTopicBefore(String stuId, Integer topicId) {
        Student student = this.findByStuId(stuId);
        Topic topic = topicDao.selectById(topicId);
        if (student == null || topic == null) {
            return R.error(HttpStatus.SC_NOT_FOUND, "系统异常:暂无数据！");
        }
        if (student.getTopicStatus() < 0) {
            return R.error(HttpStatus.SC_BAD_REQUEST, "存在反选失败的题目，请前往我的选题进行查看！");
        }
        if (student.getTopicStatus() > 0) {
            return R.error(HttpStatus.SC_BAD_REQUEST, "存在已选择的题目，请前往我的选题进行查看！");
        }
        if (topic.getSelected() >= topic.getToplimit()) {
            return R.error(HttpStatus.SC_BAD_REQUEST, "选题人数已达上限！");
        }
        if (!StringUtils.isEmpty(topic.getStuId())) {
            return R.error(HttpStatus.SC_BAD_REQUEST, "该课题已被反选，不可选择！");
        }
        return R.ok();
    }

    /**
     * 取消选题
     *
     * @param stuId
     * @param topicId
     * @return
     */
    @Override
    public R cancelTopic(String stuId, Integer topicId) {
        Student student = this.findByStuId(stuId);
        Topic topic = topicDao.selectById(topicId);
        // 等待反选/反选失败
        if (student.getTopicStatus() == 1 || student.getTopicStatus() == -1) {
            // 学生选题状态重置为待选题
            student.setTopicStatus(0);
            // 学生选题重置为空
            student.setTopicId(0);
            // 该选题所选人数-1
            topic.setSelected(topic.getSelected() - 1);
            // 判断所选人数是否小于人数限制,若小于并且当前课题无人被反选则将选题设为"可选状态"
            if (topic.getToplimit() > topic.getSelected() && StringUtils.isEmpty(topic.getStuId())) {
                topic.setStatus(0);
            }
            studentDao.updateById(student);
            topicDao.updateById(topic);
        }
        return R.ok("取消选题成功！");
    }

    @Override
    public R cancelTopicBefore(String stuId, Integer topicId) {
        Student student = this.findByStuId(stuId);
        Topic topic = topicDao.selectById(topicId);
        if (student == null || topic == null) {
            return R.error(HttpStatus.SC_NOT_FOUND, "系统异常:暂无数据！");
        }
        // 教师已反选成功,不可取消选题
        if (student.getTopicStatus() == 2) {
            return R.error(HttpStatus.SC_NOT_FOUND, "教师已反选，不可取消选题！");
        }
        return R.ok();
    }

    @Override
    public R myTopic(String stuId) {
        Student student = this.findByStuId(stuId);
        Topic topic;
        // 如果当前学生的选题结果为反选成功,则选题表对应的选题会关联学生id
        if (student.getTopicStatus().equals(2)) {
            topic = topicDao.selectOne(new QueryWrapper<Topic>().eq("stu_id", stuId));
        } else {
            // 未反选成功,tb_topic还不会存在学生id,所以间接查询
            topic = topicDao.selectById(student.getTopicId());
        }
        if (topic == null) {
            return R.error(HttpStatus.SC_NOT_FOUND, "系统异常:参数错误！");
        }
        Teacher teacher = teacherDao.selectOne(new QueryWrapper<Teacher>().eq("tid", topic.getTid()));
        // 创建Vo
        TopicVo vo = new TopicVo();
        BeanUtils.copyProperties(topic, vo);
        vo.setTname(teacher.getTname());
        vo.setHiredate(teacher.getHiredate());
        vo.setTopicStatus(student.getTopicStatus());
        return R.ok().put("myTopic", Collections.singletonList(vo));
    }


}
package org.mineok.service.impl;

import org.apache.http.HttpStatus;
import org.mineok.common.utils.R;
import org.mineok.dao.StudentDao;
import org.mineok.dao.TopicDao;
import org.mineok.entity.Student;
import org.mineok.entity.Topic;
import org.mineok.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.mineok.common.utils.PageUtils;
import org.mineok.common.utils.Query;

import javax.annotation.Resource;


@Service
public class StudentServiceImpl extends ServiceImpl<StudentDao, Student> implements StudentService {

    @Resource
    private StudentDao studentDao;
    @Resource
    private TopicDao topicDao;

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

    @Override
    public R choseTopic(String stuId, Integer topicId) {
        Student student = this.findByStuId(stuId);
        Topic topic = topicDao.selectById(topicId);
        if (topic.getSelected() >= topic.getToplimit()) {
            return R.error(HttpStatus.SC_BAD_REQUEST, "选题人数已达上限!");
        }
        if (student.getTopicStatus() > 0) {
            return R.error(HttpStatus.SC_BAD_REQUEST, "存在已选择的题目，请前往我的选题进行查看!");
        }
        // 学生选择该题目,等待老师反选
        // 选择后不能再选其他题目,并且等待反选期间不可选择其他题目
        student.setTopicId(topicId);
        student.setTopicStatus(1);
        topic.setSelected(topic.getSelected() + 1);
        studentDao.updateById(student);
        topicDao.updateById(topic);
        return R.error(HttpStatus.SC_OK, "选题成功！");
    }


}
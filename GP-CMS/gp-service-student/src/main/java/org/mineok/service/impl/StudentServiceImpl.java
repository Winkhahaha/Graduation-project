package org.mineok.service.impl;

import org.mineok.dao.StudentDao;
import org.mineok.entity.Student;
import org.mineok.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.mineok.common.utils.PageUtils;
import org.mineok.common.utils.Query;


@Service
public class StudentServiceImpl extends ServiceImpl<StudentDao, Student> implements StudentService {

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


}
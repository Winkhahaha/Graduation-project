package org.mineok.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.mineok.common.utils.PageUtils;
import org.mineok.common.utils.Query;
import org.mineok.dao.TeacherDao;
import org.mineok.entity.Teacher;
import org.mineok.service.TeacherService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("teacherService")
public class TeacherServiceImpl extends ServiceImpl<TeacherDao, Teacher> implements TeacherService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<Teacher> page = this.page(
                new Query<Teacher>().getPage(params),
                new QueryWrapper<Teacher>()
        );

        return new PageUtils(page);
    }

}
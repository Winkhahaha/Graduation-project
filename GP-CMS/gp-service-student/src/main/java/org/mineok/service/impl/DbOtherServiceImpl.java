package org.mineok.service.impl;

import org.mineok.common.utils.R;
import org.mineok.dao.DbOtherDao;
import org.mineok.dao.DbZdjsDao;
import org.mineok.dao.StudentDao;
import org.mineok.dao.TeacherDao;
import org.mineok.entity.DbOther;
import org.mineok.entity.DbZdjs;
import org.mineok.entity.Student;
import org.mineok.entity.Teacher;
import org.mineok.service.DbOtherService;
import org.mineok.vo.StuOtherVo;
import org.mineok.vo.TeacherOtherVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.mineok.common.utils.PageUtils;
import org.mineok.common.utils.Query;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;


@Service("dbOtherService")
public class DbOtherServiceImpl extends ServiceImpl<DbOtherDao, DbOther> implements DbOtherService {

    @Resource
    private TeacherDao teacherDao;
    @Resource
    private DbOtherDao otherDao;
    @Resource
    private StudentDao studentDao;
    @Resource
    private DbZdjsDao zdjsDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<DbOther> page = this.page(
                new Query<DbOther>().getPage(params),
                new QueryWrapper<DbOther>()
        );

        return new PageUtils(page);
    }

    @Override
    public R getOtherTeacherTopicList(String key, String tid) {
        Teacher teacher = teacherDao.selectOne(new QueryWrapper<Teacher>()
                .eq("tid", tid));
        List<Teacher> list = teacherDao.selectList(new QueryWrapper<Teacher>()
                .eq("group_id", teacher.getGroupId())
        );
        list.remove(teacher);
        if (CollectionUtils.isEmpty(list)) {
            return R.error("暂无数据！");
        }
        List<StuOtherVo> vos = new ArrayList<StuOtherVo>();
        // 获取每一个教师所带学生进行综合答辩的vo
        for (Teacher t : list) {
            List<StuOtherVo> stuOtherVos = otherDao.other_student_list(key, t.getTid());
            vos.addAll(stuOtherVos);
            // 清空 防止内存泄漏
            stuOtherVos.clear();
        }
        return R.ok().put("otherList", vos);
    }

    @Override
    public R addDB_Other(DbOther other) {
        if (ObjectUtils.isEmpty(other)) {
            return R.error("系统异常！");
        }
//        other.setSumScore2(sumScore(other));
        if (other.getId() != null) {
            otherDao.updateById(other);
        } else {
            otherDao.insert(other);
        }
        return R.ok("评分成功！");
    }

    @Override
    public R getDB_OtherBytid(String tid, Integer topicId) {
        DbOther other = otherDao.selectOne(new QueryWrapper<DbOther>()
                .eq("tid", tid).eq("topic_id", topicId));
        if (ObjectUtils.isEmpty(other)) {
            return R.error("暂无数据！").put("dbOther", new DbOther());
        }
        return R.ok().put("dbOther", other);
    }

    @Override
    public R getOtherScoreList(String stuId) {
        Student student = studentDao.selectOne(new QueryWrapper<Student>().eq("stu_id", stuId));
        List<TeacherOtherVo> list = otherDao.other_teacher_list(student.getTopicId());
        if (CollectionUtils.isEmpty(list)) {
            return R.error("暂无数据！");
        }
        return R.ok().put("otherScoreList", list);
    }

    @Override
    public R getFinalScore(String stuId) {
        Student student = studentDao.selectOne(new QueryWrapper<Student>().eq("stu_id", stuId));
        DbZdjs zdjs = zdjsDao.selectOne(new QueryWrapper<DbZdjs>().eq("topic_id", student.getTopicId()));
        List<DbOther> others = otherDao.selectList(new QueryWrapper<DbOther>().eq("topic_id", student.getTopicId()));
        Integer sum = zdjs.getSumScore();
        for (DbOther other : others) {
            sum += other.getSumScore2();
        }
        sum /= (others.size() + 1);
        // 喝多了写的
        DbZdjs dbZdjs = new DbZdjs();
        dbZdjs.setSumScore(sum);
        return R.ok().put("score", Collections.singletonList(dbZdjs));
    }

    private Integer sumScore(DbOther dbOther) {
        return dbOther.getQuality() + dbOther.getContent() + dbOther.getSituation() + dbOther.getStandard();
    }
}
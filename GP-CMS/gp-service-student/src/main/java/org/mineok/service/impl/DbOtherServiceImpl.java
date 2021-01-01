package org.mineok.service.impl;

import org.mineok.common.utils.R;
import org.mineok.dao.DbOtherDao;
import org.mineok.dao.TeacherDao;
import org.mineok.entity.DbOther;
import org.mineok.entity.DbZdjs;
import org.mineok.entity.Teacher;
import org.mineok.service.DbOtherService;
import org.mineok.vo.StuOtherVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.mineok.common.utils.PageUtils;
import org.mineok.common.utils.Query;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;


@Service("dbOtherService")
public class DbOtherServiceImpl extends ServiceImpl<DbOtherDao, DbOther> implements DbOtherService {

    @Resource
    private TeacherDao teacherDao;
    @Resource
    private DbOtherDao otherDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<DbOther> page = this.page(
                new Query<DbOther>().getPage(params),
                new QueryWrapper<DbOther>()
        );

        return new PageUtils(page);
    }

    @Override
    public R getOtherTeacherTopicList(String tid) {
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
            List<StuOtherVo> stuOtherVos = otherDao.other_student_list(t.getTid());
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
        other.setSumScore2(sumScore(other));
        if (other.getId() != null) {
            otherDao.updateById(other);
        } else {
            otherDao.insert(other);
        }
        return R.ok("评分成功！");
    }

    @Override
    public R getDB_OtherByTopicId(Integer topicId) {
        DbOther other = otherDao.selectOne(new QueryWrapper<DbOther>().eq("topic_id", topicId));
        if (ObjectUtils.isEmpty(other)) {
            return R.error("暂无数据！");
        }
        return R.ok().put("dbOther", other);
    }

    private Integer sumScore(DbOther dbOther) {
        return dbOther.getQuality() + dbOther.getContent() + dbOther.getSituation() + dbOther.getStandard();
    }
}
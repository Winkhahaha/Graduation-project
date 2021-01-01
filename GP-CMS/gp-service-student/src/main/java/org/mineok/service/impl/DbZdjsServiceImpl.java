package org.mineok.service.impl;

import io.swagger.models.auth.In;
import org.mineok.common.utils.R;
import org.mineok.dao.DbZdjsDao;
import org.mineok.dao.StudentDao;
import org.mineok.entity.DbZdjs;
import org.mineok.entity.Student;
import org.mineok.service.DbZdjsService;
import org.mineok.vo.StuZDJSVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

import javax.annotation.Resource;


@Service("dbZdjsService")
public class DbZdjsServiceImpl extends ServiceImpl<DbZdjsDao, DbZdjs> implements DbZdjsService {

    @Resource
    private DbZdjsDao zdjsDao;
    @Resource
    private StudentDao studentDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<DbZdjs> page = this.page(
                new Query<DbZdjs>().getPage(params),
                new QueryWrapper<DbZdjs>()
        );

        return new PageUtils(page);
    }

    /*
        添加指导教师答辩意见
     */
    @Override
    public R addDB_ZDJS(DbZdjs zdjs) {
        if (ObjectUtils.isEmpty(zdjs)) {
            return R.error("系统异常！");
        }
        zdjs.setSumScore(sumScore(zdjs));
        if (zdjs.getId() != null) {
            zdjsDao.updateById(zdjs);
        } else {
            zdjsDao.insert(zdjs);
        }
        return R.ok("评分成功！");
    }

    @Override
    public R getDB_ZDJSByTopicId(Integer topicId) {
        DbZdjs zdjs = baseMapper.selectOne(new QueryWrapper<DbZdjs>().eq("topic_id", topicId));
        if (ObjectUtils.isEmpty(zdjs)) {
            return R.error("暂无数据！");
        }
        return R.ok().put("dbZdjs", zdjs);
    }

    @Override
    public R ZDJS_Student_List(String tid) {
        List<StuZDJSVo> list = zdjsDao.ZDJS_Student_List(tid);
        if (CollectionUtils.isEmpty(list)) {
            return R.error("暂无数据！请督促学生尽快提交毕设成果！");
        }
        return R.ok().put("dbList", list);
    }

    @Override
    public R getZDJS_Score(String stuId) {
        Student student = studentDao.selectOne(new QueryWrapper<Student>().eq("stu_id", stuId));
        DbZdjs zdjs = zdjsDao.selectOne(new QueryWrapper<DbZdjs>().eq("topic_id", student.getTopicId()));
        if (ObjectUtils.isEmpty(zdjs)) {
            return R.error("暂无数据！请提交毕设成果获联系指导教师！");
        }
        return R.ok().put("myZDJSScore", Collections.singletonList(zdjs));
    }

    // 计算总分
    private Integer sumScore(DbZdjs zdjs) {
        return zdjs.getProfessionalAbility() + zdjs.getThesis() + zdjs.getStandard() + zdjs.getLanguage() + zdjs.getAttitude();
    }
}
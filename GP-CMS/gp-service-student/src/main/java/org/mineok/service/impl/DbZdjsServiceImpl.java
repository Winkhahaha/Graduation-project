package org.mineok.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.mineok.common.utils.PageUtils;
import org.mineok.common.utils.Query;
import org.mineok.common.utils.R;
import org.mineok.dao.DbZdjsDao;
import org.mineok.dao.ResultDao;
import org.mineok.dao.StudentDao;
import org.mineok.entity.DbZdjs;
import org.mineok.entity.Result;
import org.mineok.entity.Student;
import org.mineok.service.DbZdjsService;
import org.mineok.vo.StuZDJSVo;
import org.mineok.vo.StudefenceVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@Service("dbZdjsService")
public class DbZdjsServiceImpl extends ServiceImpl<DbZdjsDao, DbZdjs> implements DbZdjsService {

    @Resource
    private DbZdjsDao zdjsDao;
    @Resource
    private StudentDao studentDao;
    @Resource
    private ResultDao resultDao;

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
            return R.error("暂无数据！").put("dbZdjs", new DbZdjs());
        }
        return R.ok().put("dbZdjs", zdjs);
    }

    @Override
    public R ZDJS_Student_List(Integer defenceStatus, String tid) {
        // 根据defenceStatus区别线上2/线下1学生
        List<StuZDJSVo> list = zdjsDao.ZDJS_Student_List(defenceStatus, tid);
        if (CollectionUtils.isEmpty(list)) {
            return R.error("暂无数据！请督促学生尽快提交毕设成果！");
        }
        return R.ok().put("dbList", list);
    }

    @Override
    public R getZDJS_Score(String stuId) {
        Student student = studentDao.selectOne(new QueryWrapper<Student>().eq("stu_id", stuId));
        DbZdjs zdjs = zdjsDao.selectOne(new QueryWrapper<DbZdjs>().eq("topic_id", student.getTopicId()));
        StudefenceVo vo = new StudefenceVo();
        if (!ObjectUtils.isEmpty(zdjs)) {
            BeanUtils.copyProperties(zdjs, vo);
        }
        vo.setDefenceStatus(student.getDefenceStatus());
        return R.ok().put("myZDJSScore", Collections.singletonList(vo));
    }

    @Override
    public R stuSetDefenceStatus(Integer defenceStatus, String stuId) {
        Student student = studentDao.selectOne(new QueryWrapper<Student>().eq("stu_id", stuId));
        if (ObjectUtils.isEmpty(student)) {
            return R.error("系统异常:参数错误！");
        }
        student.setDefenceStatus(defenceStatus);
        studentDao.updateById(student);
        return R.ok("申请答辩成功！");
    }

    @Override
    public R stuSetDefenceStatusBefore(String stuId) {
        Student student = studentDao.selectOne(new QueryWrapper<Student>().eq("stu_id", stuId));
        Result result = resultDao.selectOne(new QueryWrapper<Result>().eq("stu_id", stuId));
        if (ObjectUtils.isEmpty(result)) {
            return R.error("请先添加毕设成果，并提交给指导教师进行审批！");
        }
        if (!ObjectUtils.isEmpty(result) && result.getApprovalStatus() < 2) {
            return R.error("毕设成果尚未审批完成！");
        }
        if (student.getDefenceStatus() > 0) {
            return R.error("请勿重复申请，若要修改答辩方式请先取消此次申请！");
        }
        return R.ok();
    }

    @Override
    public R stuCancelDefenceStatus(String stuId) {
        Student student = studentDao.selectOne(new QueryWrapper<Student>().eq("stu_id", stuId));
        if (ObjectUtils.isEmpty(student)) {
            return R.error("系统异常:参数错误！");
        }
        student.setDefenceStatus(0);
        studentDao.updateById(student);
        return R.ok("取消成功！");
    }

    @Override
    public R stuCancelDefenceStatusBefore(String stuId) {
        Student student = studentDao.selectOne(new QueryWrapper<Student>().eq("stu_id", stuId));
        DbZdjs zdjs = zdjsDao.selectOne(new QueryWrapper<DbZdjs>().eq("topic_id", student.getTopicId()));
        if (student.getDefenceStatus().equals(0)) {
            return R.error("您还未提交答辩申请！");
        }
        if (student.getDefenceStatus() > 0 && !ObjectUtils.isEmpty(zdjs)) {
            if (zdjs.getConclusion() > -1) {
                return R.error("当前答辩申请已获批准不可取消！");
            }
        }
        return R.ok();
    }

    // 计算总分
    private Integer sumScore(DbZdjs zdjs) {
        return zdjs.getProfessionalAbility() + zdjs.getThesis() + zdjs.getStandard() + zdjs.getLanguage() + zdjs.getAttitude();
    }
}

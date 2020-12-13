package org.mineok.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.mineok.common.utils.PageUtils;
import org.mineok.common.utils.Query;
import org.mineok.common.utils.R;
import org.mineok.dao.ResultDao;
import org.mineok.dao.StudentDao;
import org.mineok.dao.TeacherDao;
import org.mineok.dao.TopicDao;
import org.mineok.entity.*;
import org.mineok.service.ResultService;
import org.mineok.vo.ResultVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@Service("resultService")
public class ResultServiceImpl extends ServiceImpl<ResultDao, Result> implements ResultService {

    @Resource
    private StudentDao studentDao;
    @Resource
    private TeacherDao teacherDao;
    @Resource
    private TopicDao topicDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<Result> page = this.page(
                new Query<Result>().getPage(params),
                new QueryWrapper<Result>()
        );

        return new PageUtils(page);
    }

    /*
        补充方法
     */
    private Student getStuByQuery(String stuId) {
        return studentDao.selectOne(new QueryWrapper<Student>().eq("stu_id", stuId));
    }

    private Teacher getTeacherByQuery(String tid) {
        return teacherDao.selectOne(new QueryWrapper<Teacher>().eq("tid", tid));
    }

    private Result getResultByQuery(String stuId) {
        return baseMapper.selectOne(new QueryWrapper<Result>().eq("stu_id", stuId));
    }

    @Override
    public R getStuResult(String stuId) {
        Student student = this.getStuByQuery(stuId);
        if (ObjectUtils.isEmpty(student)) {
            return R.error("系统异常！");
        }
        Topic topic = topicDao.selectById(student.getTopicId());
        Teacher teacher = this.getTeacherByQuery(topic.getTid());
        Result result = this.getResultByQuery(stuId);
        if (ObjectUtils.isEmpty(topic) || ObjectUtils.isEmpty(teacher) || ObjectUtils.isEmpty(result)) {
            return R.error("系统异常！");
        }
        ResultVo resultVo = new ResultVo();
        BeanUtils.copyProperties(result, resultVo);
        resultVo.setTopicName(topic.getTopicName());
        resultVo.setStuName(student.getName());
        resultVo.setTname(teacher.getTname());
        return R.ok().put("stuResult", Collections.singletonList(resultVo));
    }

    @Override
    public R getResultInfo(Integer resultId) {
        Result result = baseMapper.selectById(resultId);
        if (ObjectUtils.isEmpty(result)) {
            return R.error("系统异常！");
        }
        return R.ok().put("result", result);
    }

    @Override
    public R saveResultBefore(String stuId) {
        Result result = this.getResultByQuery(stuId);
        if (!ObjectUtils.isEmpty(result)) {
            return R.error("已添加毕设成果！");
        }
        return R.ok();
    }

    @Override
    public R saveResult(Result result, String stuId) {
        Student student = this.getStuByQuery(stuId);
        if (ObjectUtils.isEmpty(student) || ObjectUtils.isEmpty(result)) {
            return R.error("系统异常！");
        }
        // 获取学生当前的选题
        Topic topic = topicDao.selectById(student.getTopicId());
        result.setStuId(student.getStuId());
        result.setTopicId(student.getTopicId());
        // 根据选题设置教师属性
        result.setTid(topic.getTid());
        baseMapper.insert(result);
        return R.ok("添加成功！");
    }

    @Override
    public R updateResultBefore(Integer resultId) {
        Result result = baseMapper.selectById(resultId);
        if (ObjectUtils.isEmpty(result)) {
            return R.error("系统异常！");
        }
        // -1审核未通过,1审核中,2审核通过
        if (result.getApprovalStatus() == -1 || result.getApprovalStatus() == 1) {
            return R.error("请先取消该次审批！");
        }
        if (result.getApprovalStatus() == 2) {
            return R.error("已审批成功，暂不能修改！");
        }
        return R.ok();
    }

    @Override
    public R updateResult(Result result) {
        if (ObjectUtils.isEmpty(result)) {
            return R.error("系统异常！");
        }
        baseMapper.updateById(result);
        return R.ok("修改成功！");
    }

    @Override
    public R deleteResultBefore(Integer resultId) {
        Result result = baseMapper.selectById(resultId);
        if (ObjectUtils.isEmpty(result)) {
            return R.error("系统异常！");
        }
        // 如果存在一个topic的审批状态大于0,1审批中,2审批完成,则不能执行此次删除
        if (result.getApprovalStatus() > 0) {
            return R.error("审批流程中的毕设成果，无法执行删除!");
        }
        return R.ok();
    }

    @Override
    public R deleteResult(Integer resultId) {
        baseMapper.deleteById(resultId);
        return R.ok("删除成功！");
    }

    @Override
    public R submitReprtApproval(Integer resultId) {
        Result result = baseMapper.selectById(resultId);
        if (ObjectUtils.isEmpty(result)) {
            return R.error("系统异常！");
        }
        result.setApprovalStatus(1);
        baseMapper.updateById(result);
        return R.ok("审批已提交至指导老师！");
    }

    @Override
    public R beforeSubmitReprtApproval(Integer resultId) {
        Result result = baseMapper.selectById(resultId);
        if (ObjectUtils.isEmpty(result)) {
            return R.error("系统异常！");
        }
        if (result.getApprovalStatus().equals(1)) {
            return R.error("已提交审批，请勿重复操作！");
        }
        if (result.getApprovalStatus().equals(2)) {
            return R.error("审批已通过，请勿重复操作！");
        }
        if (result.getApprovalStatus().equals(-1)) {
            return R.error("审批未通过，请先取消该次审批并进行课题修改后再进行提交！");
        }
        return R.ok();
    }

    @Override
    public R beforeCancelResultApproval(Integer resultId) {
        Result result = baseMapper.selectById(resultId);
        if (ObjectUtils.isEmpty(result)) {
            return R.error("系统异常！");
        }
        if (result.getApprovalStatus().equals(0)) {
            return R.error("请先提交审批！");
        }
        if (result.getApprovalStatus().equals(2)) {
            return R.error("该开题报告已通过审批，无法取消！");
        }
        return R.ok();
    }

    @Override
    public R cancelResultApproval(Integer resultId) {
        Result result = baseMapper.selectById(resultId);
        if (ObjectUtils.isEmpty(result)) {
            return R.error("系统异常！");
        }
        result.setApprovalStatus(0);
        baseMapper.updateById(result);
        return R.ok("取消审批成功！");
    }

    @Override
    public R getStuResultList(Integer appStatus, String tid) {
        Teacher teacher = this.getTeacherByQuery(tid);
        // 根据页面传来的不同的审批状态码呈现不同的列表
        List<Result> resultList = baseMapper.selectList(new QueryWrapper<Result>().
                eq("tid", tid).eq("approval_status", appStatus));
        if (ObjectUtils.isEmpty(teacher) || CollectionUtils.isEmpty(resultList)) {
            return R.error("系统异常！");
        }
        List<ResultVo> resultVos = new ArrayList<ResultVo>();
        for (Result result : resultList) {
            Student student = this.getStuByQuery(result.getStuId());
            Topic topic = topicDao.selectById(result.getTopicId());
            ResultVo resultVo = new ResultVo();
            BeanUtils.copyProperties(result, resultVo);
            resultVo.setTopicName(topic.getTopicName());
            resultVo.setStuName(student.getName());
            resultVo.setTname(teacher.getTname());
            resultVos.add(resultVo);
            resultVo = null;
            student = null;
            topic = null;
        }
        return R.ok().put("stuResultList", resultVos);
    }

    @Override
    public R addOpinions(String opinions, Integer resultId) {
        Result result = baseMapper.selectById(resultId);
        if (ObjectUtils.isEmpty(result)) {
            return R.error("系统异常:参数错误！");
        }
        result.setOpinions(opinions);
        baseMapper.updateById(result);
        return R.ok("审批意见添加成功！");
    }

    @Override
    public R commitApproval(Integer resultId) {
        Result result = baseMapper.selectById(resultId);
        if (ObjectUtils.isEmpty(result)) {
            return R.error("系统异常:参数错误！");
        }
        // 设置审批通过状态
        result.setApprovalStatus(2);
        baseMapper.updateById(result);
        return R.ok("已确认该报告审批！");
    }

    @Override
    public R rejectApproval(Integer resultId) {
        Result result = baseMapper.selectById(resultId);
        if (ObjectUtils.isEmpty(result)) {
            return R.error("系统异常:参数错误！");
        }
        if (StringUtils.isEmpty(result.getOpinions())) {
            return R.error("驳回申请前必须添加审批意见！");
        }
        // 设置审批驳回状态
        result.setApprovalStatus(-1);
        baseMapper.updateById(result);
        return R.ok("已驳回该开题报告！");
    }
}
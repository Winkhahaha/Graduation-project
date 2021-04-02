package org.mineok.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.mineok.common.utils.PageUtils;
import org.mineok.common.utils.Query;
import org.mineok.common.utils.R;
import org.mineok.dao.*;
import org.mineok.entity.*;
import org.mineok.service.MidService;
import org.mineok.vo.MidVo;
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


@Service("midService")
public class MidServiceImpl extends ServiceImpl<MidDao, Mid> implements MidService {

    @Resource
    private StudentDao studentDao;
    @Resource
    private TeacherDao teacherDao;
    @Resource
    private TopicDao topicDao;
    @Resource
    private ReportDao reportDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<Mid> page = this.page(
                new Query<Mid>().getPage(params),
                new QueryWrapper<Mid>()
        );

        return new PageUtils(page);
    }

    /**
     * 获取当前学生的报告
     *
     * @param stuId
     * @return
     */
    @Override
    public R getStuMid(String stuId) {
        Student student = this.getStuByQuery(stuId);
        if (ObjectUtils.isEmpty(student)) {
            return R.error("系统异常！");
        }
        Topic topic = topicDao.selectById(student.getTopicId());
        if (ObjectUtils.isEmpty(topic)) {
            return R.error("系统异常！");
        }
        Teacher teacher = this.getTeacherByQuery(topic.getTid());
        Mid mid = this.getMidByQuery(stuId);
        if (ObjectUtils.isEmpty(teacher) || ObjectUtils.isEmpty(mid)) {
            return R.error("系统异常！");
        }
        MidVo midVo = new MidVo();
        BeanUtils.copyProperties(mid, midVo);
        midVo.setTopicName(topic.getTopicName());
        midVo.setStuName(student.getStuName());
        midVo.setTname(teacher.getTname());
        return R.ok().put("stuMid", Collections.singletonList(midVo));
    }

    @Override
    public R getMidInfo(Integer midId) {
        Mid mid = baseMapper.selectById(midId);
        if (ObjectUtils.isEmpty(mid)) {
            return R.error("系统异常！");
        }
        return R.ok().put("mid", mid);
    }

    @Override
    public R saveMidBefore(String stuId) {
        Report report = reportDao.selectOne(new QueryWrapper<Report>().eq("stu_id", stuId));
        // 先判断是否添加过开题报告
        if (ObjectUtils.isEmpty(report)) {
            return R.error("请先添加开题报告！");
        }
        if (report.getApprovalStatus() != 2) {
            return R.error("开题报告未通过审批，暂不能添加中期资料！");
        }
        Mid mid = this.getMidByQuery(stuId);
        if (!ObjectUtils.isEmpty(mid)) {
            return R.error("已添加中期资料请勿重复操作！");
        }
        return R.ok();
    }

    @Override
    public R saveMid(Mid mid, String stuId) {
        Student student = this.getStuByQuery(stuId);
        if (ObjectUtils.isEmpty(student) || ObjectUtils.isEmpty(mid)) {
            return R.error("系统异常！");
        }
        // 获取学生当前的选题
        Topic topic = topicDao.selectById(student.getTopicId());
        mid.setStuId(student.getStuId());
        mid.setTopicId(student.getTopicId());
        // 根据选题设置教师属性
        mid.setTid(topic.getTid());
        baseMapper.insert(mid);
        return R.ok("添加成功！");
    }

    @Override
    public R updateMidBefore(Integer midId) {
        Mid mid = baseMapper.selectById(midId);
        if (ObjectUtils.isEmpty(mid)) {
            return R.error("系统异常！");
        }
        // -1审核未通过,1审核中,2审核通过
        if (mid.getApprovalStatus() == -1 || mid.getApprovalStatus() == 1) {
            return R.error("请先取消该次查验！");
        }
        if (mid.getApprovalStatus() == 2) {
            return R.error("已通过查验，暂不能修改！");
        }
        return R.ok();
    }

    @Override
    public R updateMid(Mid mid) {
        if (ObjectUtils.isEmpty(mid)) {
            return R.error("系统异常！");
        }
        baseMapper.updateById(mid);
        return R.ok("修改成功！");
    }

    @Override
    public R deleteMidBefore(Integer midId) {
        Mid mid = baseMapper.selectById(midId);
        if (ObjectUtils.isEmpty(mid)) {
            return R.error("系统异常！");
        }
        // 如果存在一个topic的审批状态大于0,1审批中,2审批完成,则不能执行此次删除
        if (mid.getApprovalStatus() > 0) {
            return R.error("查验中的中期资料，无法执行删除!");
        }
        return R.ok();
    }

    @Override
    public R deleteMid(Integer midId) {
        baseMapper.deleteById(midId);
        return R.ok("删除成功！");
    }

    private Student getStuByQuery(String stuId) {
        return studentDao.selectOne(new QueryWrapper<Student>().eq("stu_id", stuId));
    }

    private Teacher getTeacherByQuery(String tid) {
        return teacherDao.selectOne(new QueryWrapper<Teacher>().eq("tid", tid));
    }

    private Mid getMidByQuery(String stuId) {
        return baseMapper.selectOne(new QueryWrapper<Mid>().eq("stu_id", stuId));
    }

    @Override
    public R submitMidApproval(Integer midId) {
        Mid mid = baseMapper.selectById(midId);
        if (ObjectUtils.isEmpty(mid)) {
            return R.error("系统异常！");
        }
        mid.setApprovalStatus(1);
        baseMapper.updateById(mid);
        return R.ok("已提交至指导老师进行查验！");
    }

    @Override
    public R beforeSubmitMidApproval(Integer midId) {
        Mid mid = baseMapper.selectById(midId);
        if (ObjectUtils.isEmpty(mid)) {
            return R.error("系统异常！");
        }
        if (mid.getApprovalStatus().equals(1)) {
            return R.error("已提交查验，请勿重复操作！");
        }
        if (mid.getApprovalStatus().equals(2)) {
            return R.error("已通过验收，请勿重复操作！");
        }
        if (mid.getApprovalStatus().equals(-1)) {
            return R.error("查验未通过，请先取消该次查验并进行中期资料修改后再进行提交！");
        }
        return R.ok();
    }

    @Override
    public R beforeCancelMidApproval(Integer midId) {
        Mid mid = baseMapper.selectById(midId);
        if (ObjectUtils.isEmpty(mid)) {
            return R.error("系统异常！");
        }
        if (mid.getApprovalStatus().equals(0)) {
            return R.error("请先提交查验！");
        }
        if (mid.getApprovalStatus().equals(2)) {
            return R.error("该中期资料已通过验收，无法取消！");
        }
        return R.ok();
    }

    @Override
    public R cancelMidApproval(Integer midId) {
        Mid mid = baseMapper.selectById(midId);
        if (ObjectUtils.isEmpty(mid)) {
            return R.error("系统异常！");
        }
        mid.setApprovalStatus(0);
        baseMapper.updateById(mid);
        return R.ok("取消查验成功！");
    }

    @Override
    public R getStuMidList(Integer appStatus, String tid) {
        Teacher teacher = this.getTeacherByQuery(tid);
        // 根据页面传来的不同的审批状态码呈现不同的列表
        List<Mid> midList = baseMapper.selectList(new QueryWrapper<Mid>().
                eq("tid", tid).eq("approval_status", appStatus));
        if (ObjectUtils.isEmpty(teacher) || CollectionUtils.isEmpty(midList)) {
            return R.error("系统异常！");
        }
        List<MidVo> midVos = new ArrayList<MidVo>();
        for (Mid mid : midList) {
            Student student = this.getStuByQuery(mid.getStuId());
            Topic topic = topicDao.selectById(mid.getTopicId());
            MidVo midVo = new MidVo();
            BeanUtils.copyProperties(mid, midVo);
            midVo.setTopicName(topic.getTopicName());
            midVo.setStuName(student.getStuName());
            midVo.setTname(teacher.getTname());
            midVos.add(midVo);
        }
        return R.ok().put("stuMidList", midVos);
    }

    @Override
    public R addOpinions(String opinions, Integer midId) {
        Mid mid = baseMapper.selectById(midId);
        if (ObjectUtils.isEmpty(mid)) {
            return R.error("系统异常:参数错误！");
        }
        mid.setOpinions(opinions);
        baseMapper.updateById(mid);
        return R.ok("验收意见添加成功！");
    }

    @Override
    public R addMidScore(Integer midScore, Integer midId) {
        Mid mid = baseMapper.selectById(midId);
        if (ObjectUtils.isEmpty(mid)) {
            return R.error("系统异常:参数错误！");
        }
        mid.setMidScore(midScore);
        baseMapper.updateById(mid);
        return R.ok("中期评分成功！");
    }

    @Override
    public R commitApproval(Integer midId) {
        Mid mid = baseMapper.selectById(midId);
        if (ObjectUtils.isEmpty(mid)) {
            return R.error("系统异常:参数错误！");
        }
        if (mid.getMidScore() == null) {
            return R.error("请先填写中期评分表！");
        }
        // 设置审批通过状态
        mid.setApprovalStatus(2);
        baseMapper.updateById(mid);
        return R.ok("已确认该中期查验！");
    }

    @Override
    public R rejectApproval(Integer midId) {
        Mid mid = baseMapper.selectById(midId);
        if (ObjectUtils.isEmpty(mid)) {
            return R.error("系统异常:参数错误！");
        }
        if (StringUtils.isEmpty(mid.getOpinions())) {
            return R.error("验收未通过前必须添加查验意见！");
        }
        // 设置审批驳回状态
        mid.setApprovalStatus(-1);
        baseMapper.updateById(mid);
        return R.ok("已驳回该中期资料！");
    }
}

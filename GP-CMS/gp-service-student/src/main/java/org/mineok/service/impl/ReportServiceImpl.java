package org.mineok.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.mineok.common.utils.PageUtils;
import org.mineok.common.utils.Query;
import org.mineok.common.utils.R;
import org.mineok.dao.ReportDao;
import org.mineok.dao.StudentDao;
import org.mineok.dao.TeacherDao;
import org.mineok.dao.TopicDao;
import org.mineok.entity.Report;
import org.mineok.entity.Student;
import org.mineok.entity.Teacher;
import org.mineok.entity.Topic;
import org.mineok.service.ReportService;
import org.mineok.vo.ReportVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;


@Service("reportService")
public class ReportServiceImpl extends ServiceImpl<ReportDao, Report> implements ReportService {

    @Resource
    private StudentDao studentDao;
    @Resource
    private TeacherDao teacherDao;
    @Resource
    private TopicDao topicDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<Report> page = this.page(
                new Query<Report>().getPage(params),
                new QueryWrapper<Report>()
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
    public R getStuReport(String stuId) {
        Student student = this.getStuByQuery(stuId);
        if (ObjectUtils.isEmpty(student)) {
            return R.error("系统异常！");
        }
        Topic topic = topicDao.selectById(student.getTopicId());
        Teacher teacher = this.getTeacherByQuery(topic.getTid());
        Report report = this.getReportByQuery(stuId);
        if (ObjectUtils.isEmpty(topic) || ObjectUtils.isEmpty(teacher) || ObjectUtils.isEmpty(report)) {
            return R.error("系统异常！");
        }
        ReportVo reportVo = new ReportVo();
        BeanUtils.copyProperties(report, reportVo);
        reportVo.setTopicName(topic.getTopicName());
        reportVo.setStuName(student.getName());
        reportVo.setTname(teacher.getTname());
        return R.ok().put("stuReport", Collections.singletonList(reportVo));
    }

    @Override
    public R getReportInfo(Integer reportId) {
        Report report = baseMapper.selectById(reportId);
        if (ObjectUtils.isEmpty(report)) {
            return R.error("系统异常！");
        }
        return R.ok().put("report", report);
    }

    @Override
    public R saveReportBefore(String stuId) {
        Report report = this.getReportByQuery(stuId);
        if (!ObjectUtils.isEmpty(report)) {
            return R.error("已添加开题报告！");
        }
        return R.ok();
    }

    @Override
    public R saveReport(Report report, String stuId) {
        Student student = this.getStuByQuery(stuId);
        if (ObjectUtils.isEmpty(student) || ObjectUtils.isEmpty(report)) {
            return R.error("系统异常！");
        }
        // 获取学生当前的选题
        Topic topic = topicDao.selectById(student.getTopicId());
        report.setStuId(student.getStuId());
        report.setTopicId(student.getTopicId());
        // 根据选题设置教师属性
        report.setTid(topic.getTid());
        baseMapper.insert(report);
        return R.ok("添加成功！");
    }

    @Override
    public R updateReportBefore(Integer reportId) {
        Report report = baseMapper.selectById(reportId);
        if (ObjectUtils.isEmpty(report)) {
            return R.error("系统异常！");
        }
        // -1审核未通过,1审核中,2审核通过
        if (report.getApprovalStatus() == -1 || report.getApprovalStatus() == 1) {
            return R.error("请先取消该次审批！");
        }
        if (report.getApprovalStatus() == 2) {
            return R.error("已审批成功，暂不能修改！");
        }
        return R.ok();
    }

    @Override
    public R updateReport(Report report) {
        if (ObjectUtils.isEmpty(report)) {
            return R.error("系统异常！");
        }
        baseMapper.updateById(report);
        return R.ok("修改成功！");
    }

    @Override
    public R deleteReportBefore(Integer reportId) {
        Report report = baseMapper.selectById(reportId);
        if (ObjectUtils.isEmpty(report)) {
            return R.error("系统异常！");
        }
        // 如果存在一个topic的审批状态大于0,1审批中,2审批完成,则不能执行此次删除
        if (report.getApprovalStatus() > 0) {
            return R.error("审批流程中的开题报告，无法执行删除!");
        }
        return R.ok();
    }

    @Override
    public R deleteReport(Integer reportId) {
        baseMapper.deleteById(reportId);
        return R.ok("删除成功！");
    }

    private Student getStuByQuery(String stuId) {
        return studentDao.selectOne(new QueryWrapper<Student>().eq("stu_id", stuId));
    }

    private Teacher getTeacherByQuery(String tid) {
        return teacherDao.selectOne(new QueryWrapper<Teacher>().eq("tid", tid));
    }

    private Report getReportByQuery(String stuId) {
        return baseMapper.selectOne(new QueryWrapper<Report>().eq("stu_id", stuId));
    }

    @Override
    public R submitReprtApproval(Integer reportId) {
        Report report = baseMapper.selectById(reportId);
        if (ObjectUtils.isEmpty(report)) {
            return R.error("系统异常！");
        }
        report.setApprovalStatus(1);
        baseMapper.updateById(report);
        return R.ok("审批已提交至指导老师！");
    }

    @Override
    public R beforeSubmitReprtApproval(Integer reportId) {
        Report report = baseMapper.selectById(reportId);
        if (ObjectUtils.isEmpty(report)) {
            return R.error("系统异常！");
        }
        if (report.getApprovalStatus().equals(1)) {
            return R.error("已提交审批，请勿重复操作！");
        }
        if (report.getApprovalStatus().equals(2)) {
            return R.error("审批已通过，请勿重复操作！");
        }
        if (report.getApprovalStatus().equals(-1)) {
            return R.error("审批未通过，请先取消该次审批并进行课题修改后再进行提交！");
        }
        return R.ok();
    }

    @Override
    public R beforeCancelReportApproval(Integer reportId) {
        Report report = baseMapper.selectById(reportId);
        if (ObjectUtils.isEmpty(report)) {
            return R.error("系统异常！");
        }
        if (report.getApprovalStatus().equals(0)) {
            return R.error("请先提交审批！");
        }
        if (report.getApprovalStatus().equals(2)) {
            return R.error("该开题报告已通过审批，无法取消！");
        }
        return R.ok();
    }

    @Override
    public R cancelReportApproval(Integer reportId) {
        Report report = baseMapper.selectById(reportId);
        if (ObjectUtils.isEmpty(report)) {
            return R.error("系统异常！");
        }
        report.setApprovalStatus(0);
        baseMapper.updateById(report);
        return R.ok("取消审批成功！");
    }

    @Override
    public R getStuReportList(Integer appStatus, String tid) {
        Teacher teacher = this.getTeacherByQuery(tid);
        // 根据页面传来的不同的审批状态码呈现不同的列表
        List<Report> reportList = baseMapper.selectList(new QueryWrapper<Report>().
                eq("tid", tid).eq("approval_status", appStatus));
        if (ObjectUtils.isEmpty(teacher) || CollectionUtils.isEmpty(reportList)) {
            return R.error("系统异常！");
        }
        List<ReportVo> reportVos = new ArrayList<ReportVo>();
        for (Report report : reportList) {
            Student student = this.getStuByQuery(report.getStuId());
            Topic topic = topicDao.selectById(report.getTopicId());
            ReportVo reportVo = new ReportVo();
            BeanUtils.copyProperties(report, reportVo);
            reportVo.setTopicName(topic.getTopicName());
            reportVo.setStuName(student.getName());
            reportVo.setTname(teacher.getTname());
            reportVos.add(reportVo);
            reportVo = null;
            student = null;
            topic = null;
        }
        return R.ok().put("stuReportList", reportVos);
    }

    @Override
    public R addOpinions(String opinions, Integer reportId) {
        Report report = baseMapper.selectById(reportId);
        if (ObjectUtils.isEmpty(report)) {
            return R.error("系统异常:参数错误！");
        }
        report.setOpinions(opinions);
        baseMapper.updateById(report);
        return R.ok("审批意见添加成功！");
    }

    @Override
    public R commitApproval(Integer reportId) {
        Report report = baseMapper.selectById(reportId);
        if (ObjectUtils.isEmpty(report)) {
            return R.error("系统异常:参数错误！");
        }
        // 设置审批通过状态
        report.setApprovalStatus(2);
        baseMapper.updateById(report);
        return R.ok("已确认该报告审批！");
    }

    @Override
    public R rejectApproval(Integer reportId) {
        Report report = baseMapper.selectById(reportId);
        if (ObjectUtils.isEmpty(report)) {
            return R.error("系统异常:参数错误！");
        }
        if (StringUtils.isEmpty(report.getOpinions())) {
            return R.error("驳回申请前必须添加审批意见！");
        }
        // 设置审批驳回状态
        report.setApprovalStatus(-1);
        baseMapper.updateById(report);
        return R.ok("已驳回该开题报告！");
    }
}
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

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;


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
        ReportVo reportVo = new ReportVo();
        BeanUtils.copyProperties(report, reportVo);
        reportVo.setTopicName(topic.getTopicName());
        reportVo.setStuName(student.getName());
        reportVo.setTname(teacher.getTname());
        return R.ok().put("stuReport", Collections.singletonList(reportVo));
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

}
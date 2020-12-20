package org.mineok.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.mineok.common.utils.PageUtils;
import org.mineok.common.utils.Query;
import org.mineok.common.utils.R;
import org.mineok.dao.*;
import org.mineok.entity.*;
import org.mineok.service.ResultService;
import org.mineok.vo.ResultVo;
import org.mineok.vo.TopicVo;
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
    @Resource
    private DirectorDao directorDao;
    @Resource
    private ReportDao reportDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<Result> page = this.page(
                new Query<Result>().getPage(params),
                new QueryWrapper<Result>()
        );

        return new PageUtils(page);
    }

    public ResultVo createResultVoByResult(Result result) {
        if (ObjectUtils.isEmpty(result)) {
            return null;
        }
        Student student = this.getStuByQuery(result.getStuId());
        Topic topic = topicDao.selectById(result.getTopicId());
        Teacher teacher = this.getTeacherByQuery(result.getTid());
        ResultVo resultVo = new ResultVo();
        BeanUtils.copyProperties(result, resultVo);
        resultVo.setTopicName(topic.getTopicName());
        resultVo.setStuName(student.getName());
        resultVo.setTname(teacher.getTname());
        return resultVo;
    }

    @Override
    public R queryResultsByApproval(Map<String, Object> params) {
        IPage<ResultVo> page = new Query<ResultVo>().getPage(params);
        String key = params.get("key").toString();
        List<Result> resultList = baseMapper.selectList(new QueryWrapper<Result>()
                .eq("approval_status", 3)
                .like(!StringUtils.isEmpty(key), "result_name", key)
                .or()
                .eq("approval_status", 3)
                .like(!StringUtils.isEmpty(key), "stu_id", key)
                .orderByAsc("createtime"));
        // 得到所有通过终审的毕设列表
        if (CollectionUtils.isEmpty(resultList)) {
            return R.error("系统异常！");
        }
        List<ResultVo> resultVos = new ArrayList<ResultVo>();
        for (Result result : resultList) {
            ResultVo vo = this.createResultVoByResult(result);
            if (!ObjectUtils.isEmpty(vo)) {
                resultVos.add(vo);
            }
        }
        page.setRecords(resultVos);
        page.setTotal(resultVos.size());
        return R.ok().put("page", new PageUtils(page));
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

    private Director getDirectorByQuery(String directorId) {
        return directorDao.selectOne(new QueryWrapper<Director>().eq("director_id", directorId));
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

    // 通过学生信息创建ResltVo查询
    private ResultVo createResultVoByStudent(Student student) {
        Result result = this.getResultByQuery(student.getStuId());
        if (ObjectUtils.isEmpty(result)) {
            return null;
        }
        Topic topic = topicDao.selectById(result.getTopicId());
        Teacher teacher = this.getTeacherByQuery(result.getTid());
        ResultVo resultVo = new ResultVo();
        BeanUtils.copyProperties(result, resultVo);
        resultVo.setTopicName(topic.getTopicName());
        resultVo.setStuName(student.getName());
        resultVo.setTname(teacher.getTname());
        return resultVo;
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
//        // 先查找该学生是否已经选题成功
//        Topic topic = topicDao.selectOne(new QueryWrapper<Topic>().eq("stu_id", stuId));
//        if (ObjectUtils.isEmpty(topic)) {
//            return R.error("请先进行选题！");
//        }
        Report report = reportDao.selectOne(new QueryWrapper<Report>().eq("stu_id", stuId));
        // 先判断是否添加过开题报告
        if (ObjectUtils.isEmpty(report)) {
            return R.error("请先添加开题报告！");
        }
        if (report.getApprovalStatus() != 2) {
            return R.error("开题报告未通过审批，暂不能添加毕设成果！");
        }
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
            return R.error("终审中，暂不能修改！");
        }
        if (result.getApprovalStatus() == 3) {
            return R.error("终审成功，不能修改！");
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
    public R submitResultApproval(Integer resultId) {
        Result result = baseMapper.selectById(resultId);
        if (ObjectUtils.isEmpty(result)) {
            return R.error("系统异常！");
        }
        result.setApprovalStatus(1);
        baseMapper.updateById(result);
        return R.ok("审批已提交至指导老师！");
    }

    @Override
    public R beforeSubmitResultApproval(Integer resultId) {
        Result result = baseMapper.selectById(resultId);
        if (ObjectUtils.isEmpty(result)) {
            return R.error("系统异常！");
        }
        if (result.getApprovalStatus().equals(1)) {
            return R.error("已提交审批，请勿重复操作！");
        }
        if (result.getApprovalStatus().equals(2)) {
            return R.error("终审中，请勿重复操作！");
        }
        if (result.getApprovalStatus().equals(3)) {
            return R.error("终审已通过，请勿重复操作！");
        }
        if (result.getApprovalStatus() < 0) {
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
            return R.error("已提交终审，暂无法取消！");
        }
        if (result.getApprovalStatus().equals(3)) {
            return R.error("终审已通过，暂无法取消！");
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
        List<Result> resultList = null;
        if (appStatus.equals(2)) {
            // 教师的提交终审列表,应该既包含终审中,也包含终审成功
            resultList = baseMapper.selectList(new QueryWrapper<Result>().
                    eq("tid", tid).eq("approval_status", 2).or()
                    .eq("tid", tid)
                    .eq("approval_status", 3));
        } else {
            resultList = baseMapper.selectList(new QueryWrapper<Result>().
                    eq("tid", tid).eq("approval_status", appStatus));
        }
        if (ObjectUtils.isEmpty(teacher) || CollectionUtils.isEmpty(resultList)) {
            return R.error("系统异常！");
        }
        List<ResultVo> resultVos = new ArrayList<ResultVo>();
        for (Result result : resultList) {
            ResultVo vo = this.createResultVoByResult(result);
            if (!ObjectUtils.isEmpty(vo)) {
                resultVos.add(vo);
            }
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
        return R.ok("已提交该毕设成果至负责人进行终审！");
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
        return R.ok("已驳回该毕设成果审批！");
    }

    @Override
    public R getFinalApprovalList(Integer appStatus, String directorId) {
        Director director = this.getDirectorByQuery(directorId);
        if (ObjectUtils.isEmpty(director)) {
            return R.error("系统异常！");
        }
        // 获取院系Id
        Integer deptId = director.getDeptId();
        // 根据院系Id查找所有学生
        List<Student> studentList = studentDao.selectList(new QueryWrapper<Student>().eq("dept_id", deptId));
        if (CollectionUtils.isEmpty(studentList)) {
            return R.error("系统异常！");
        }
        ArrayList<ResultVo> vos = new ArrayList<ResultVo>();
        for (Student student : studentList) {
            // 判断学生列表里面哪些学生已经有毕设成果记录
            ResultVo vo = createResultVoByStudent(student);
            // 根据审批状态呈现不同的Vo列表
            if (!ObjectUtils.isEmpty(vo) && vo.getApprovalStatus().equals(appStatus)) {
                vos.add(vo);
            }
        }
        return R.ok().put("stuResultList", vos);
    }

    @Override
    public R commitFinalApproval(Integer resultId) {
        Result result = baseMapper.selectById(resultId);
        if (ObjectUtils.isEmpty(result)) {
            return R.error("系统异常:参数错误！");
        }
        // 设置审批通过状态
        result.setApprovalStatus(3);
        baseMapper.updateById(result);
        return R.ok("终审通过！");
    }

    @Override
    public R rejectFinalApproval(Integer resultId) {
        Result result = baseMapper.selectById(resultId);
        if (ObjectUtils.isEmpty(result)) {
            return R.error("系统异常:参数错误！");
        }
        if (StringUtils.isEmpty(result.getOpinions())) {
            return R.error("驳回终审前必须添加审批意见！");
        }
        // 设置审批驳回状态
        result.setApprovalStatus(-2);
        baseMapper.updateById(result);
        return R.ok("已驳回该毕设成果终审！");
    }

}
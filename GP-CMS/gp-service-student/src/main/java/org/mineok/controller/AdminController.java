package org.mineok.controller;

import org.mineok.common.annotation.SysLog;
import org.mineok.common.utils.PageUtils;
import org.mineok.common.utils.R;
import org.mineok.entity.*;
import org.mineok.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * @Author Gaoming
 * @Email mineok@foxmail.com
 * @Date 2020/12/14/ 20:53
 * @Description
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private ReportService reportService;
    @Autowired
    private DirectorService directorService;
    @Autowired
    private ResultService resultService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private TopicService topicService;
    @Autowired
    private SysLogService logService;


    // 获取gp-service-student日志数据
    //@SysLog("查看系统日志")
    @ResponseBody
    @GetMapping("/log/list")
    public R logList(@RequestParam Map<String, Object> params){
        PageUtils page = logService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 全盘操作表数据
     */
    @RequestMapping("/director/list")
    public R directorlist(@RequestParam Map<String, Object> params){
        PageUtils page = directorService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/director/info/{id}")
    public R directorinfo(@PathVariable("id") Integer id){
        Director director = directorService.getById(id);
        return R.ok().put("director", director);
    }

    /**
     * 保存
     */
    @RequestMapping("/director/save")
    public R directorsave(@RequestBody Director director){
        directorService.save(director);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/director/update")
    public R directorupdate(@RequestBody Director director){
        directorService.updateById(director);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/director/delete")
    public R directordelete(@RequestBody Integer[] ids){
        directorService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

    /**
     * 列表
     */
    @RequestMapping("/report/list")
    public R reportlist(@RequestParam Map<String, Object> params){
        PageUtils page = reportService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/report/info/{reportId}")
    public R reportinfo(@PathVariable("reportId") Integer reportId){
        Report report = reportService.getById(reportId);
        return R.ok().put("report", report);
    }

    /**
     * 保存
     */
    @RequestMapping("/report/save")
    public R reportsave(@RequestBody Report report){
        reportService.save(report);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/report/update")
    public R reportupdate(@RequestBody Report report){
        reportService.updateById(report);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/report/delete")
    public R reportdelete(@RequestBody Integer[] reportIds){
        reportService.removeByIds(Arrays.asList(reportIds));
        return R.ok();
    }

    @RequestMapping("/result/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = resultService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/result/info/{resultId}")
    public R resultinfo(@PathVariable("resultId") Integer resultId){
        Result result = resultService.getById(resultId);
        return R.ok().put("result", result);
    }

    /**
     * 保存
     */
    @RequestMapping("/result/save")
    public R resultsave(@RequestBody Result result){
        resultService.save(result);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/result/update")
    public R resultupdate(@RequestBody Result result){
        resultService.updateById(result);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/result/delete")
    public R resultdelete(@RequestBody Integer[] resultIds){
        resultService.removeByIds(Arrays.asList(resultIds));
        return R.ok();
    }

    @RequestMapping("/student/list")
    public R studentlist(@RequestParam Map<String, Object> params){
        PageUtils page = studentService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/student/info/{id}")
    public R studentinfo(@PathVariable("id") Integer id){
        Student student = studentService.getById(id);
        return R.ok().put("student", student);
    }

    /**
     * 保存
     */
    @RequestMapping("/student/save")
    public R studentsave(@RequestBody Student student){
        studentService.save(student);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/student/update")
    public R studentupdate(@RequestBody Student student){
        studentService.updateById(student);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/student/delete")
    public R studentdelete(@RequestBody Integer[] ids){
        studentService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

    @RequestMapping("/teacher/list")
    public R teacherlist(@RequestParam Map<String, Object> params){
        PageUtils page = teacherService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/teacher/info/{id}")
    public R teacherinfo(@PathVariable("id") Integer id){
        Teacher teacher = teacherService.getById(id);
        return R.ok().put("teacher", teacher);
    }

    /**
     * 保存
     */
    @RequestMapping("/teacher/save")
    public R teachersave(@RequestBody Teacher teacher){
        teacherService.save(teacher);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/teacher/update")
    public R teacherupdate(@RequestBody Teacher teacher){
        teacherService.updateById(teacher);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/teacher/delete")
    public R teacherdelete(@RequestBody Integer[] ids){
        teacherService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

    @RequestMapping("/topic/list")
    public R topiclist(@RequestParam Map<String, Object> params){
        PageUtils page = topicService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/topic/info/{topicId}")
    public R topicinfo(@PathVariable("topicId") Integer topicId){
        Topic topic = topicService.getById(topicId);
        return R.ok().put("topic", topic);
    }

    /**
     * 保存
     */
    @RequestMapping("/topic/save")
    public R topicsave(@RequestBody Topic topic){
        topicService.save(topic);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/topic/update")
    public R topicupdate(@RequestBody Topic topic){
        topicService.updateById(topic);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/topic/delete")
    public R topicdelete(@RequestBody Integer[] topicIds){
        topicService.removeByIds(Arrays.asList(topicIds));
        return R.ok();
    }

}

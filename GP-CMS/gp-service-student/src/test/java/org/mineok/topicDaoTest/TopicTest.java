package org.mineok.topicDaoTest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mineok.StudentApplication;
import org.mineok.common.utils.R;
import org.mineok.dao.*;
import org.mineok.entity.DbZdjs;
import org.mineok.entity.Student;
import org.mineok.entity.Teacher;
import org.mineok.service.StudentService;
import org.mineok.service.TopicService;
import org.mineok.vo.FinalScoreVo;
import org.mineok.vo.ResultVo;
import org.mineok.vo.StuZDJSVo;
import org.mineok.vo.TopicVo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author Gaoming
 * @Email mineok@foxmail.com
 * @Date 2020/12/07/ 16:27
 * @Description
 */

@SpringBootTest(classes = StudentApplication.class)
@RunWith(SpringRunner.class)
public class TopicTest {

    @Resource
    TopicDao topicDao;
    @Resource
    TopicService topicService;
    @Resource
    StudentService studentService;
    @Resource
    ResultDao resultDao;
    @Resource
    DbZdjsDao zdjsDao;
    @Resource
    TeacherDao teacherDao;
    @Resource
    StudentDao studentDao;

    /**
     * 多表查询topic- techer
     * 分页
     */
    @Test
    public void test_topicVO_topicList() {
        // System.out.println(topicDao.topicListCanChose());
        // 多表分页查询测试
        Page<TopicVo> page = new Page<TopicVo>(1, 10);
        page.setRecords(topicDao.topicListCanChose(page, 1, "张"));
        System.out.println(page.getRecords());
        System.out.println(page.getTotal());
    }

    @Test
    public void test_resultVO_resultList() {
        // 多表分页查询测试
        Page<ResultVo> page = new Page<ResultVo>(1, 2);
        List<ResultVo> list = resultDao.resultList(page, "王");
        page.setRecords(list);
        System.out.println(page.getRecords());
        System.out.println(page.getTotal());
    }

    // 归档
    @Test
    public void test_resultVO_resultList2() {
        // 多表分页查询测试
        Page<ResultVo> page = new Page<ResultVo>(1, 5);
        List<ResultVo> list = resultDao.filingList(page,null,"2017");
        page.setRecords(list);
        System.out.println(page.getRecords());
        System.out.println(page.getTotal());
    }

    @Test
    public void test_Mytopic() {
        R r = studentService.myTopic("17060211101");
        System.out.println(r.get("myTopic"));
    }

    @Test
    public void test_InvertTopicList() {
        System.out.println(topicDao.invertTopicList("1701"));
    }

    @Test
    public void test_topicInfo() {
        Object topicInfo = topicService.topicInfo(1).get("topicInfo");
        System.out.println(topicInfo);
    }

    @Test
    public void ZDJS_Student_List() {
//        List<StuZDJSVo> list = zdjsDao.ZDJS_Student_List("1702");
//        System.out.println(list);
    }

    @Test
    public void Other_Group() {
        zdjsDao.selectList(new QueryWrapper<DbZdjs>()
                .groupBy(""));
    }

    @Test
    public void teacher_Group() {
        Teacher teacher = teacherDao.selectOne(new QueryWrapper<Teacher>()
                .eq("tid", "1702"));
        List<Teacher> list = teacherDao.selectList(new QueryWrapper<Teacher>()
                .eq("group_id", 2)
        );
        list.remove(teacher);
        System.out.println(list);
    }

    @Test
    public void student_date_2017() {
        List<Student> list = studentDao.selectList(new QueryWrapper<Student>()
                .like("edudate", "2017"));
        System.out.println(list);
    }

    @Test
    public void scoreLevel() {
        FinalScoreVo vo = FinalScoreVo.getScore(66);
        System.out.println(vo.getScore() + " " + vo.getLevel());
        FinalScoreVo vo2 = FinalScoreVo.getScore(76);
        System.out.println(vo2.getScore() + " " + vo2.getLevel());
        FinalScoreVo vo3 = FinalScoreVo.getScore(86);
        System.out.println(vo3.getScore() + " " + vo3.getLevel());
        FinalScoreVo vo4 = FinalScoreVo.getScore(96);
        System.out.println(vo4.getScore() + " " + vo4.getLevel());
        FinalScoreVo vo5 = FinalScoreVo.getScore(100);
        System.out.println(vo5.getScore() + " " + vo5.getLevel());
    }

    @Resource
    private FilingDao filingDao;
    @Test
    public void finalScoreList() {
//        List<Integer> list = filingDao.scoreFilingList("2017");
//        System.out.println(list);
        new BigDecimal((float) 1 / 3).setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();

    }

}

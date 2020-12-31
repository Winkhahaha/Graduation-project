package org.mineok.topicDaoTest;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mineok.StudentApplication;
import org.mineok.common.utils.R;
import org.mineok.dao.DbZdjsDao;
import org.mineok.dao.ResultDao;
import org.mineok.dao.TopicDao;
import org.mineok.service.StudentService;
import org.mineok.service.TopicService;
import org.mineok.vo.ResultVo;
import org.mineok.vo.StuZDJSVo;
import org.mineok.vo.TopicVo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
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
        List<StuZDJSVo> list = zdjsDao.ZDJS_Student_List("1702");
        System.out.println(list);
    }
}

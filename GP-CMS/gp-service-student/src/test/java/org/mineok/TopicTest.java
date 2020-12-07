package org.mineok;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mineok.dao.TopicDao;
import org.mineok.vo.TopicVO;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

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

    /**
     * 多表查询topic- techer
     * 分页
     */
    @Test
    public void test_topicVO_topicList() {
        // System.out.println(topicDao.topicListCanChose());
        // 多表分页查询测试
        Page<TopicVO> page = new Page<TopicVO>(1, 10);
        page.setRecords(topicDao.topicListCanChose(page));
        System.out.println(page.getRecords());
        System.out.println(page.getTotal());
    }
}

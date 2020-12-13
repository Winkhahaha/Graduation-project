package org.mineok.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.http.HttpStatus;
import org.mineok.common.utils.PageUtils;
import org.mineok.common.utils.Query;
import org.mineok.common.utils.R;
import org.mineok.dao.StudentDao;
import org.mineok.dao.TeacherDao;
import org.mineok.dao.TopicDao;
import org.mineok.entity.Student;
import org.mineok.entity.Teacher;
import org.mineok.entity.Topic;
import org.mineok.service.TopicService;
import org.mineok.vo.InvertTopicVo;
import org.mineok.vo.TopicVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Service("topicService")
public class TopicServiceImpl extends ServiceImpl<TopicDao, Topic> implements TopicService {

    @Resource
    private TopicDao topicDao;
    @Resource
    private TeacherDao teacherDao;
    @Resource
    private StudentDao studentDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<Topic> page = this.page(
                new Query<Topic>().getPage(params),
                new QueryWrapper<Topic>()
        );

        return new PageUtils(page);
    }

    @Override
    public R getTopics(Map<String, Object> params,String stuId) {
//        Page<TopicVO> page = new Page<TopicVO>(
//                Long.parseLong(params.get("page").toString())
//                , Long.parseLong(params.get("limit").toString()));
        IPage<TopicVo> page = new Query<TopicVo>().getPage(params);
        Student student = studentDao.selectOne(new QueryWrapper<Student>().eq("stu_id", stuId));
        if (ObjectUtils.isEmpty(student)) {
            return R.error("系统异常！");
        }
        List<TopicVo> topicVos = topicDao.topicListCanChose(page,student.getDeptId());
        if (CollectionUtils.isEmpty(topicVos)) {
            return R.error("系统异常！");
        }
        page.setRecords(topicVos);
        page.setTotal(topicVos.size());
        return R.ok().put("page",new PageUtils(page));
    }

    @Override
    public R getInvertTopicsByTeacherId(String tid) {
        List<InvertTopicVo> list = topicDao.invertTopicList(tid);
        if (CollectionUtils.isEmpty(list)) {
            return R.error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "暂无待反选的题目！");
        }
        return R.ok().put("invertTopic", list);
    }

    /**
     * 获取选题详情(关联教师信息)
     *
     * @param topicId
     * @return
     */
    @Override
    public R topicInfo(Integer topicId) {
        Topic topic = topicDao.selectById(topicId);
        Teacher teacher = teacherDao.selectOne(new QueryWrapper<Teacher>().eq("tid", topic.getTid()));
        if (teacher == null || topic == null) {
            return R.error(HttpStatus.SC_NOT_FOUND, "系统异常:参数错误！");
        }
        TopicVo vo = new TopicVo();
        BeanUtils.copyProperties(topic, vo);
        // 关联老师信息
        vo.setTname(teacher.getTname());
        vo.setPhone(teacher.getPhone());
        return R.ok().put("topic", vo);
    }

    /**
     * 获取当前老师的所有选题
     *
     * @param tid
     * @return
     */
    @Override
    public R getTopicByTeacherId(String tid) {
        List<Topic> topicList = topicDao.selectList(new QueryWrapper<Topic>().eq("tid", tid));
        if (CollectionUtils.isEmpty(topicList)) {
            return R.error(HttpStatus.SC_NOT_FOUND, "系统异常:参数错误！");
        }
        return R.ok().put("topicList", topicList);
    }

}
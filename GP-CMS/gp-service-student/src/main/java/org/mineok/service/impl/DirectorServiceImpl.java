package org.mineok.service.impl;

import org.apache.http.HttpStatus;
import org.mineok.common.utils.R;
import org.mineok.dao.DirectorDao;
import org.mineok.dao.TeacherDao;
import org.mineok.dao.TopicDao;
import org.mineok.entity.Director;
import org.mineok.entity.Teacher;
import org.mineok.entity.Topic;
import org.mineok.service.DirectorService;
import org.mineok.vo.TopicVo;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.mineok.common.utils.PageUtils;
import org.mineok.common.utils.Query;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;


@Service("directorService")
public class DirectorServiceImpl extends ServiceImpl<DirectorDao, Director> implements DirectorService {

    @Resource
    private TopicDao topicDao;
    @Resource
    private TeacherDao teacherDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<Director> page = this.page(
                new Query<Director>().getPage(params),
                new QueryWrapper<Director>()
        );

        return new PageUtils(page);
    }

    @Override
    public R dirInfoByDirId(String directorId) {
        Director director = baseMapper.selectOne(new QueryWrapper<Director>().eq("director_id", directorId));
        if (director == null) {
            return R.error("系统异常:参数错误！");
        }
        return R.ok().put("director", Collections.singletonList(director));
    }

    /**
     * @param params     通过params.get("appStatus")获取页面状态码,判断是 待审批 驳回 审批成功三个页面哪一个发来的请求
     * @param directorId
     * @return
     */
    @Override
    public R approvalTopicList(Map<String, Object> params, String directorId) {
        Director director = baseMapper.selectOne(new QueryWrapper<Director>().eq("director_id", directorId));
        // 获取所有归属当前负责人并且状态为待审批的申报课题
        if (director == null) {
            return R.error("系统异常:参数错误！");
        }
        // 获取当前页面状态码,确认属于三种状态的哪一种状态页面
        int appStatus = Integer.parseInt(params.get("appStatus").toString());
        List<Topic> topicList = topicDao.selectList(new QueryWrapper<Topic>().eq("dept_id", director.getDeptId()).
                eq("approval_status", appStatus));
        if (CollectionUtils.isEmpty(topicList)) {
            return R.error("系统异常:参数错误！");
        }
        IPage<Topic> page = new Query<Topic>().getPage(params);
        page.setRecords(topicList);
        return R.ok().put("page", new PageUtils(page));
    }


    @Override
    public R commitApprovalTopic(Integer topicId) {
        Topic topic = topicDao.selectById(topicId);
        if (ObjectUtils.isEmpty(topic)) {
            return R.error("系统异常:参数错误！");
        }
        // 设置审批通过状态
        topic.setApprovalStatus(2);
        topicDao.updateById(topic);
        return R.ok("已确认该审批！");
    }

    @Override
    public R cancelApprovalTopic(Integer topicId) {
        Topic topic = topicDao.selectById(topicId);
        if (ObjectUtils.isEmpty(topic)) {
            return R.error("系统异常:参数错误！");
        }
        // 设置审批通过状态
        topic.setApprovalStatus(-1);
        topicDao.updateById(topic);
        return R.ok("已驳回该审批！");
    }

    @Override
    public R teacherList(String directorId) {
        Director director = baseMapper.selectOne(new QueryWrapper<Director>().eq("director_id", directorId));
        if (ObjectUtils.isEmpty(director)) {
            return R.error("系统异常:参数错误！");
        }
        List<Teacher> list = teacherDao.selectList(new QueryWrapper<Teacher>().eq("dept_id", director.getDeptId()));
        if (CollectionUtils.isEmpty(list)) {
            return R.error("系统异常:参数错误！");
        }
        return R.ok().put("teacherList", list);
    }

    @Override
    public R setTopicCount(Integer topicCount, String tid) {
        Teacher teacher = teacherDao.selectOne(new QueryWrapper<Teacher>().eq("tid", tid));
        if (ObjectUtils.isEmpty(teacher)) {
            return R.error("系统异常:参数错误！");
        }
        teacher.setTopicCount(topicCount);
        teacherDao.updateById(teacher);
        return R.ok("设置成功！");
    }

}
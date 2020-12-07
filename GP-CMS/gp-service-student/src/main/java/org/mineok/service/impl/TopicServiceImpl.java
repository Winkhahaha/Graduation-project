package org.mineok.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.mineok.common.utils.PageUtils;
import org.mineok.common.utils.Query;
import org.mineok.dao.TopicDao;
import org.mineok.entity.Topic;
import org.mineok.service.TopicService;
import org.mineok.vo.TopicVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Service("topicService")
public class TopicServiceImpl extends ServiceImpl<TopicDao, Topic> implements TopicService {

    @Resource
    private TopicDao topicDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<Topic> page = this.page(
                new Query<Topic>().getPage(params),
                new QueryWrapper<Topic>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils getTopics(Map<String, Object> params) {
//        Page<TopicVO> page = new Page<TopicVO>(
//                Long.parseLong(params.get("page").toString())
//                , Long.parseLong(params.get("limit").toString()));
        IPage<TopicVO> page = new Query<TopicVO>().getPage(params);
        page.setRecords(topicDao.topicListCanChose(page));
        return new PageUtils(page);
    }

}
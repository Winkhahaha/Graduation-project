package org.mineok.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.mineok.common.utils.PageUtils;
import org.mineok.common.utils.R;
import org.mineok.entity.DbOther;

import java.util.Map;

/**
 * @author GaoMing
 * @email mineok@foxmail.com
 * @date 2020-12-31 14:41:34
 */
public interface DbOtherService extends IService<DbOther> {

    PageUtils queryPage(Map<String, Object> params);

    // 获取评阅小组中除当前指导教师外的其他教师所带的符合答辩要求的学生
    R getOtherTeacherTopicList(String key, String tid);

    // 添加评审小组教师评分表
    R addDB_Other(DbOther other);

    // 获取评审小组评分表详情
    R getDB_OtherBytid(String tid);
}


package org.mineok.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.mineok.common.utils.PageUtils;
import org.mineok.common.utils.R;
import org.mineok.entity.Director;

import java.util.Map;

/**
 * @author GaoMing
 * @email mineok@foxmail.com
 * @date 2020-12-09 15:33:56
 */
public interface DirectorService extends IService<Director> {

    PageUtils queryPage(Map<String, Object> params);

    R dirInfoByDirId(String directorId);

    // 获取当前负责人审批的课题
    R approvalTopicList(Map<String, Object> params, String directorId);

    // 审批通过课题
    R commitApprovalTopic(Integer topicId);

    // 驳回课题
    R cancelApprovalTopic(Integer topicId);

    // 获取负责人管理的老师
    R teacherList(String directorId);

    // 设置老师的最大课题数
    R setTopicCount(Integer topicCount, String tid);

}


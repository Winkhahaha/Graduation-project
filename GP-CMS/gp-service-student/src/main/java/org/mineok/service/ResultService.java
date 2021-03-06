package org.mineok.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.mineok.common.utils.PageUtils;
import org.mineok.common.utils.R;
import org.mineok.entity.Result;
import org.mineok.entity.Result;

import java.util.Map;

/**
 * 毕设成果
 *
 * @author GaoMing
 * @email mineok@foxmail.com
 * @date 2020-12-07 14:30:27
 */
public interface ResultService extends IService<Result> {

    PageUtils queryPage(Map<String, Object> params);

    // 展示所有通过终审的毕设成果
    R queryResultsByApproval(Map<String, Object> params);

    // 获取当前学生的毕设成果
    R getStuResult(String stuId);

    R getResultInfo(Integer resultId);

    // 添加毕设成果前判断
    R saveResultBefore(String stuId);

    // 添加毕设成果
    R saveResult(Result result, String stuId);

    // 更新毕设成果前判断
    R updateResultBefore(Integer resultId);

    // 更新毕设成果
    R updateResult(Result result);

    // 删除前判断
    R deleteResultBefore(Integer resultId);

    // 删除毕设成果
    R deleteResult(Integer resultId);

    // 学生:提交毕设成果审批
    R submitResultApproval(Integer resultId);

    // 学生:提交毕设成果审批之前
    R beforeSubmitResultApproval(Integer resultId);

    // 学生:取消审批
    R cancelResultApproval(Integer resultId);

    // 学生:取消审批之前
    R beforeCancelResultApproval(Integer resultId);

    // 教师:得到所指导学生的成果列表
    R getStuResultList(Integer appStatus, String tid);

    // 设置毕设成果的审批意见
    R addOpinions(String opinions, Integer resultId);

    // 教师:确认成果审批
    R commitApproval(Integer resultId);

    // 教师:驳回成果审批
    R rejectApproval(Integer resultId);

    // 院系负责人:查看该院系待终审的成果列表
    R getFinalApprovalList(Integer appStatus, String directorId);

    // 负责人:确认成果终审
    R commitFinalApproval(Integer resultId);

    // 负责人:驳回成果终审
    R rejectFinalApproval(Integer resultId);

    // 获取按入学日期归档的优秀毕设成果
    R queryFilingList(Map<String, Object> params);

}


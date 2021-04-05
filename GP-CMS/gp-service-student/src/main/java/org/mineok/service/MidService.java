package org.mineok.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.mineok.common.utils.PageUtils;
import org.mineok.common.utils.R;
import org.mineok.entity.Mid;

import java.util.Map;

/**
 * 中期资料
 *
 * @author GaoMing
 * @email mineok@foxmail.com
 * @date 2020-12-07 14:30:27
 */
public interface MidService extends IService<Mid> {

    PageUtils queryPage(Map<String, Object> params);

    // 获取当前学生的中期资料
    R getStuMid(String stuId);

    R getMidInfo(Integer midId);

    // 添加中期资料前判断
    R saveMidBefore(String stuId);

    // 添加中期资料
    R saveMid(Mid mid, String stuId);

    // 更新中期资料前判断
    R updateMidBefore(Integer midId);

    // 更新中期资料
    R updateMid(Mid mid);

    // 删除前判断
    R deleteMidBefore(Integer midId);

    // 删除中期资料
    R deleteMid(Integer midId);

    // 学生:提交中期资料审批
    R submitMidApproval(Integer midId);

    // 学生:提交中期资料审批之前
    R beforeSubmitMidApproval(Integer midId);

    // 学生:取消审批
    R cancelMidApproval(Integer midId);

    // 学生:取消审批之前
    R beforeCancelMidApproval(Integer midId);

    // 教师:得到所指导学生的中期资料列表
    R getStuMidList(Integer appStatus, String tid);

    // 教师:设置中期资料的审批意见
    R addOpinions(String opinions, Integer midId);

    R addMidScore(Integer midScore, Integer midId);

    // 教师:确认中期资料审批
    R commitApproval(Integer midId);

    // 教师:驳回中期资料审批
    R rejectApproval(Integer midId);
}


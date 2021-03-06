package org.mineok.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 中期 - 实体
 *
 * @author GaoMing
 * @email mineok@foxmail.com
 * @date 2020-12-07 14:30:27
 */
@Data
@TableName("tb_mid")
public class Mid implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Integer midId;
    /**
     * 中期报告名称
     */
    private String midName;
    /**
     * 报告对应的学生学号
     */
    private String stuId;
    /**
     * 报告对应的教师工号
     */
    private String tid;
    /**
     * 报告创建时间
     */
    private Date createtime;
    /**
     * 报告审批状态:0待审核/-1不通过/1通过
     */
    private Integer approvalStatus;
    /**
     * 教师审批意见/备注
     */
    private String opinions;
    /**
     * 文件地址
     */
    private String dataUrl;
    /**
     * 报告对应的课题id
     */
    private Integer topicId;
    /**
     * 中期得分
     */
    private Integer midScore;
}

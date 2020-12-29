package org.mineok.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 学生 - 实体
 *
 * @author GaoMing
 * @email mineok@foxmail.com
 * @date 2020-12-07 14:30:27
 */
@Data
@TableName("tb_student")
public class Student implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Integer id;
    /**
     *
     */
    private String stuName;
    /**
     * 0false:男,1true:女
     */
    private Boolean gender;
    /**
     * 学号,也是用户登录的id
     */
    private String stuId;
    /**
     * 入学年份
     */
    private Date edudate;
    /**
     * 专业
     */
    private String science;
    /**
     * 选题Id
     */
    private Integer topicId;
    /**
     * 学生选题状态(结果):0待选题,1等待老师反选,2反选成功,-1反选失败,下一轮选题
     */
    private Integer topicStatus;
    /**
     * 学生所在院系
     */
    private Integer deptId;
}

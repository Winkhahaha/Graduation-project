package org.mineok.vo;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @Author Gaoming
 * @Email mineok@foxmail.com
 * @Date 2020/12/07/ 16:01
 * @Description 选题列表信息 - VO
 */
@Data
@ToString
public class TopicVO {
    /**
     * 选题名称
     */
    private String toname;
    /**
     * 创建时间
     */
    private Date createtime;
    /**
     * 选题人数上限
     */
    private Integer toplimit;
    /**
     * 选题状态:0可选,1不可选
     */
    private Integer status;
    /**
     * 所属专业
     */
    private String science;
    /**
     * 指导教师姓名
     */
    private String tname;
    /**
     * 教师入职日期
     */
    private Date hiredate;
}

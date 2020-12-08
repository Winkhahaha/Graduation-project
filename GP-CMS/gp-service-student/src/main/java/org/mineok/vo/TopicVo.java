package org.mineok.vo;

import lombok.Data;
import lombok.ToString;
import org.mineok.entity.Topic;

import java.util.Date;

/**
 * @Author Gaoming
 * @Email mineok@foxmail.com
 * @Date 2020/12/07/ 20:50
 * @Description 选题列表信息 - VO
 */
@Data
@ToString
public class TopicVo extends Topic {

    /**
     * 指导教师姓名
     */
    private String tname;
    /**
     * 指导老师联系电话
     */
    private String phone;
    /**
     * 教师入职日期
     */
    private Date hiredate;
    /**
     * 学生选题状态
     */
    private Integer topicStatus;
}

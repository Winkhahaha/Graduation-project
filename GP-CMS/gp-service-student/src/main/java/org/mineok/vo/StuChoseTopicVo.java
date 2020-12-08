package org.mineok.vo;

import lombok.Data;
import lombok.ToString;
import org.mineok.entity.Topic;

import java.util.Date;

/**
 * @Author Gaoming
 * @Email mineok@foxmail.com
 * @Date 2020/12/07/ 20:50
 * @Description 学生选题列表信息 - VO
 */
@Data
@ToString
public class StuChoseTopicVo extends Topic {

    /**
     * 指导教师姓名
     */
    private String tname;
    /**
     * 教师入职日期
     */
    private Date hiredate;
    /**
     * 当前选题状态
     */
    private Integer topicStatus;
}

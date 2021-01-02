package org.mineok.vo;

import lombok.Data;

/**
 * @Author Gaoming
 * @Email mineok@foxmail.com
 * @Date 2020/12/31/ 18:49
 * @Description 指导教师答辩学生列表
 */
@Data
public class StuOtherVo {

    private String stuName;

    private String stuId;

    private Integer topicId;

    private String topicName;

//    // otherId
//    private Integer id;

    // 指导教师评分
    private Integer sumScore;

    // 教师综合表评分
//    private Integer sumScore2;

}

package org.mineok.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Author Gaoming
 * @Email mineok@foxmail.com
 * @Date 2020/12/31/ 18:49
 * @Description 指导教师答辩学生列表
 */
@Data
public class StuZDJSVo {

    private String stuName;

    private String stuId;

    private Integer topicId;

    private String topicName;

    // zdjsId
    private Integer id;

    // 答辩状态
    private Integer conclusion;

    // 总分
    private Integer sumScore;

    // 答辩时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date schedule;

    // 答辩地点
    private String place;
}

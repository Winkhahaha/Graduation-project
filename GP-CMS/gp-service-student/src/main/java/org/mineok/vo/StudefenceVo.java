package org.mineok.vo;

import lombok.Data;
import org.mineok.entity.DbZdjs;

/**
 * @Author Gaoming
 * @Email mineok@foxmail.com
 * @Date 2021/01/05/ 11:16
 * @Description
 */
@Data
public class StudefenceVo extends DbZdjs {

    // 学生提交的答辩状态
    private Integer defenceStatus;
}

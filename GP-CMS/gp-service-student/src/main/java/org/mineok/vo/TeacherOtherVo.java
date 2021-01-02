package org.mineok.vo;

import lombok.Data;
import org.mineok.entity.DbOther;

/**
 * @Author Gaoming
 * @Email mineok@foxmail.com
 * @Date 2021/01/02/ 14:23
 * @Description  学生查看评阅小组教师评分
 */
@Data
public class TeacherOtherVo extends DbOther {

    private String tname;
}

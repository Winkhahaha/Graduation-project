package org.mineok.vo;

import lombok.Data;
import lombok.ToString;
import org.mineok.entity.Report;

/**
 * @Author Gaoming
 * @Email mineok@foxmail.com
 * @Date 2020/12/11/ 16:51
 * @Description
 */
@Data
@ToString
public class ReportVo extends Report {

    private String topicName;

    private String stuName;

    private String tname;
}

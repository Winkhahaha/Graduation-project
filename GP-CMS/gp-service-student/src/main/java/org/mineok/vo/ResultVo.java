package org.mineok.vo;

import lombok.Data;
import lombok.ToString;
import org.mineok.entity.Report;
import org.mineok.entity.Result;

/**
 * @Author Gaoming
 * @Email mineok@foxmail.com
 * @Date 2020/12/11/ 16:51
 * @Description
 */
@Data
@ToString
public class ResultVo extends Result {

    private String topicName;

    private String stuName;

    private String tname;

    // 所属专业
    private String science;

}
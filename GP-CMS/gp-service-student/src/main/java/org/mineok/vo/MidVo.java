package org.mineok.vo;

import lombok.Data;
import lombok.ToString;
import org.mineok.entity.Mid;

/**
 * @Author Gaoming
 * @Email mineok@foxmail.com
 * @Date 2020/12/11/ 16:51
 * @Description
 */
@Data
@ToString
public class MidVo extends Mid {

    private String topicName;

    private String stuName;

    private String tname;
}

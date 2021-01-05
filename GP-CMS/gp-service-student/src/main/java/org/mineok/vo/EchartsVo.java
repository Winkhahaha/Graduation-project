package org.mineok.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author Gaoming
 * @Email mineok@foxmail.com
 * @Date 2021/01/05/ 16:44
 * @Description echarts圆饼需要的数据格式
 */
@Data
@AllArgsConstructor
public class EchartsVo {
    private String name;
    private Object value;
}

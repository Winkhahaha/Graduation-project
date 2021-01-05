package org.mineok.service;

import org.apache.ibatis.annotations.Mapper;
import org.mineok.common.utils.R;

/**
 * @Author Gaoming
 * @Email mineok@foxmail.com
 * @Date 2021/01/05/ 16:10
 * @Description 归档图表业务
 */
public interface FilingService {

    // 圆饼
    R getScoreLevel(String enrollmentYear);

    // 柱状图
    R getScoreLevel2(String enrollmentYear);
}

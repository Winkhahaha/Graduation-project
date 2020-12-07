package org.mineok.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.mineok.common.utils.PageUtils;
import org.mineok.entity.Report;

import java.util.Map;

/**
 * 
 * 开题报告
 * @author GaoMing
 * @email mineok@foxmail.com
 * @date 2020-12-07 14:30:27
 */
public interface ReportService extends IService<Report> {

    PageUtils queryPage(Map<String, Object> params);
}

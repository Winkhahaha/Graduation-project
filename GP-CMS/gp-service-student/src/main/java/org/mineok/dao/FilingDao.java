package org.mineok.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.mineok.entity.Result;
import org.mineok.vo.ResultVo;

import java.util.List;

/**
 * @author GaoMing
 * @email mineok@foxmail.com
 * @date 2020-12-07 14:30:27
 */
@Mapper
public interface FilingDao  {

    // 归档:获取同一届学生的分数情况
    List<Integer> scoreFilingList(@Param("enrollmentYear") String enrollmentYear);
}

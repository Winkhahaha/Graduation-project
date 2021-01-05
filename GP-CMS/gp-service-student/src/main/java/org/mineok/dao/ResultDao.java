package org.mineok.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.mineok.entity.Result;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.mineok.vo.ResultVo;

import java.util.List;

/**
 * @author GaoMing
 * @email mineok@foxmail.com
 * @date 2020-12-07 14:30:27
 */
@Mapper
public interface ResultDao extends BaseMapper<Result> {

    List<ResultVo> resultList(IPage<ResultVo> page, @Param("key") String key);

    // 归档:优秀成果列表
    List<ResultVo> filingList(IPage<ResultVo> page, @Param("key") String key,
                              @Param("enrollmentYear") String enrollmentYear);

}

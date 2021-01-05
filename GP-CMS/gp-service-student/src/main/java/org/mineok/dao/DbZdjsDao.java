package org.mineok.dao;

import org.apache.ibatis.annotations.Param;
import org.mineok.entity.DbZdjs;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.mineok.vo.StuZDJSVo;

import java.util.List;

/**
 * @author GaoMing
 * @email mineok@foxmail.com
 * @date 2020-12-31 14:41:35
 */
@Mapper
public interface DbZdjsDao extends BaseMapper<DbZdjs> {

    List<StuZDJSVo> ZDJS_Student_List(@Param("defenceStatus") Integer defenceStatus, @Param("tid") String tid);

}

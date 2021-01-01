package org.mineok.dao;

import org.mineok.entity.DbOther;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.mineok.vo.StuOtherVo;

import java.util.List;

/**
 * 
 * 
 * @author GaoMing
 * @email mineok@foxmail.com
 * @date 2020-12-31 14:41:34
 */
@Mapper
public interface DbOtherDao extends BaseMapper<DbOther> {

    List<StuOtherVo> other_student_list(String tid);

}

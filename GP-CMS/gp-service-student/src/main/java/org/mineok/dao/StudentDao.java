package org.mineok.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.mineok.entity.StudentEntity;

/**
 * 
 * 
 * @author G
 * @email mineok@foxmail.com
 * @date 2020-11-25 20:14:11
 */
@Mapper
public interface StudentDao extends BaseMapper<StudentEntity> {
	
}

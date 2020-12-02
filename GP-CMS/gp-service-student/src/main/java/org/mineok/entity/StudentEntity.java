package org.mineok.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author G
 * @email mineok@foxmail.com
 * @date 2020-11-25 20:14:11
 */
@Data
@TableName("tb_student")
public class StudentEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private Boolean gender;
	/**
	 * 
	 */
	private Integer age;
	/**
	 * 
	 */
	private Date birthday;
	/**
	 * 
	 */
	private String remarks;

}

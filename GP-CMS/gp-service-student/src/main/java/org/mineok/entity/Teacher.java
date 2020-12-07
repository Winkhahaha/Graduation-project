package org.mineok.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 教师 - 实体
 * @author GaoMing
 * @email mineok@foxmail.com
 * @date 2020-12-07 14:30:27
 */
@Data
@TableName("tb_teacher")
public class Teacher implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 教职工工号,系统登录名,关联选题表
	 */
	private String tid;
	/**
	 * 
	 */
	private String tname;
	/**
	 * 性别 0false男 1true女
	 */
	private Boolean gender;
	/**
	 * 入职日期
	 */
	private Date hiredate;
	/**
	 * 联系电话
	 */
	private String phone;
	/**
	 * 隶属学院/部门
	 */
	private String dept;

}

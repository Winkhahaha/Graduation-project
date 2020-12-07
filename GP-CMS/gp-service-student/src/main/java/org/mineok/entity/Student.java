package org.mineok.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 学生 - 实体
 * @author GaoMing
 * @email mineok@foxmail.com
 * @date 2020-12-07 14:30:27
 */
@Data
@TableName("tb_student")
public class Student implements Serializable {
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
	 * 0false:男,1true:女
	 */
	private Boolean gender;
	/**
	 * 学号,也是用户登录的id
	 */
	private String stuId;
	/**
	 * 入学年份
	 */
	private Date edudate;
	/**
	 * 专业
	 */
	private String science;
	/**
	 * 选题Id
	 */
	private Integer topicId;
	/**
	 * 选题状态
	 */
	private Integer topicStatus;
}

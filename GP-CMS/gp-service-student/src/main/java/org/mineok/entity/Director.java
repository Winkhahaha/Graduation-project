package org.mineok.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 院系课题负责人
 * @author GaoMing
 * @email mineok@foxmail.com
 * @date 2020-12-09 15:33:56
 */
@Data
@TableName("tb_director")
public class Director implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 学院课题负责人工号,也是系统登录账号
	 */
	private String directorId;
	/**
	 * 负责人姓名
	 */
	private String dirName;
	/**
	 * 负责人联系电话
	 */
	private String phone;
	/**
	 * 所管理学院的id
	 */
	private Integer deptId;
	/**
	 * 所管理学院名称
	 */
	private String department;

}

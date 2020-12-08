package org.mineok.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 选题 - 实体
 * @author GaoMing
 * @email mineok@foxmail.com
 * @date 2020-12-07 14:30:27
 */
@Data
@TableName("tb_topic")
public class Topic implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 课题Id
	 */
	@TableId
	private Integer topicId;
	/**
	 * 课题老师工号
	 */
	private String tid;
	/**
	 * 选题名称
	 */
	private String topicName;
	/**
	 * 课题来源
	 */
	private String source;
	/**
	 * 创建时间
	 */
	private Date createtime;
	/**
	 * 选题人数上限
	 */
	private Integer toplimit;
	/**
	 * 已选人数
	 */
	private Integer selected;
	/**
	 * 选题状态:0可选,1不可选
	 * 不可选有两种状态:1.人数达到上限 2.该题目老师已反选一人
	 */
	private Integer status;
	/**
	 * 课题类型
	 */
	private String type;
	/**
	 * 课题内容简介
	 */
	private String content;
	/**
	 * 所属院系
	 */
	private String department;
	/**
	 * 所属专业
	 */
	private String science;
	/**
	 * 课题的要求条件
	 */
	private String demand;
	/**
	 * 学号
	 */
	private String stuId;

}

package org.mineok.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 
 * 
 * @author GaoMing
 * @email mineok@foxmail.com
 * @date 2020-12-31 14:41:35
 * 指导教师评阅
 */
@Data
@TableName("tb_db_zdjs")
public class DbZdjs implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 课题Id
	 */
	private Integer topicId;
	/**
	 * 业务能力与水平
	 */
	private Integer professionalAbility;
	/**
	 * 论文质量
	 */
	private Integer thesis;
	/**
	 * 规范性
	 */
	private Integer standard;
	/**
	 * 外语水平
	 */
	private Integer language;
	/**
	 * 工作态度
	 */
	private Integer attitude;
	/**
	 * 总分
	 */
	private Integer sumScore;
	/**
	 * 指导教师评语
	 */
	private String zdjsComment;
	/**
	 * 结论:1同意按期答辩2延期答辩-1不同意答辩
	 */
	private Integer conclusion;
	/**
	 * 评阅时间
	 */
	private Date createtime;
	/**
	 * 指导教师教师id
	 */
	private String tid;
	/**
	 * 答辩日期时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai")
	private Date schedule;
	/**
	 * 答辩地点
	 */
	private String place;

}

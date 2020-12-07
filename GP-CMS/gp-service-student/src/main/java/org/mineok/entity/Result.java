package org.mineok.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 毕设成果 - 实体
 * @author GaoMing
 * @email mineok@foxmail.com
 * @date 2020-12-07 14:30:27
 */
@Data
@TableName("tb_result")
public class Result implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer resultId;
	/**
	 * 成果名称
	 */
	private String resultName;
	/**
	 * 成果简介
	 */
	private String resultContent;
	/**
	 * 成果审批状态:0待审批/-1审批不通过/1导师审批通过/2答辩通过
	 */
	private Integer status;
	/**
	 * 成果创建时间
	 */
	private Date createtime;
	/**
	 * 成果下载路径
	 */
	private String dataUrl;
	/**
	 * 成果对应的学生学号
	 */
	private String stuId;
	/**
	 * 成果对应的指导老师
	 */
	private String tid;
	/**
	 * 教师审批意见
	 */
	private String opinions;

}

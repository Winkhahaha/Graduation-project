package org.mineok.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author GaoMing
 * @email mineok@foxmail.com
 * @date 2020-12-31 14:41:34
 * 其他教师评阅
 */
@Data
@TableName("tb_db_other")
public class DbOther implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 课题id
	 */
	private Integer topicId;
	/**
	 * 设计质量
	 */
	private Integer quality;
	/**
	 * 内容阐述
	 */
	private Integer content;
	/**
	 * 答辩情况
	 */
	private Integer situation;
	/**
	 * 规范性
	 */
	private Integer standard;
	/**
	 * 总分
	 */
	private Integer sumScore2;
	/**
	 * 结论
	 */
	private String conclusion;
	/**
	 * 评阅日期
	 */
	private Date createtime;
	/**
	 * 
	 */
	private String tid;

}

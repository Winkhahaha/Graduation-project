package org.mineok.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.ToString;
import org.mineok.entity.Student;

/**
 * @Author Gaoming
 * @Email mineok@foxmail.com
 * @Date 2020/12/08/ 16:30
 * @Description 教师反选 - Vo
 */
@Data
@ToString
public class InvertTopicVo extends Student {

    /**
     * 课题Id
     */
    private Integer topicId;
    /**
     * 选题名称
     */
    private String topicName;
    /**
     * 课题专业要求
     */
    private String topicScience;

}

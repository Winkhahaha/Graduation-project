package org.mineok.vo;

import lombok.Data;

/**
 * @Author Gaoming
 * @Email mineok@foxmail.com
 * @Date 2021/01/03/ 14:09
 * @Description 最终答辩分数Vo
 */
@Data
public class FinalScoreVo {

    Integer score;

    String level;

    public static FinalScoreVo getScore(Integer score) {
        FinalScoreVo vo = new FinalScoreVo();
        vo.setScore(score);
        vo.setLevel(getLevelByScore(score));
        return vo;
    }

    private static String getLevelByScore(Integer score) {
        String level;
        score /= 10;
        switch (score) {
            case 10:
            case 9:
                level = "优秀";
                break;
            case 8:
                level = "良";
                break;
            case 7:
                level = "中";
                break;
            case 6:
                level = "及格";
                break;
            default:
                level = "不及格";
        }
        return level;
    }
}

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
        score /= 10;
        switch (score) {
            case 10:
            case 9:
                return "优秀";
            case 8:
                return "良";
            case 7:
                return "中";
            case 6:
                return "及格";
            default:
                return "不及格";
        }
    }
}

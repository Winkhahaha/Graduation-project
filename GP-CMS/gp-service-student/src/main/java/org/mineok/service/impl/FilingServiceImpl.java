package org.mineok.service.impl;

import org.mineok.common.utils.R;
import org.mineok.dao.FilingDao;
import org.mineok.service.FilingService;
import org.mineok.vo.EchartsVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Gaoming
 * @Email mineok@foxmail.com
 * @Date 2021/01/05/ 16:17
 * @Description 归档数据分析
 */
@Service("FilingService")
public class FilingServiceImpl implements FilingService {

    @Resource
    private FilingDao filingDao;

    @Override
    public R getScoreLevel(String enrollmentYear) {
        List<Integer> scoreList = filingDao.scoreFilingList(enrollmentYear);
        List levelList = levelList(scoreList);
        return R.ok().put("levelList", levelList);
    }

    @Override
    public R getScoreLevel2(String enrollmentYear) {
        List<Integer> scoreList = filingDao.scoreFilingList(enrollmentYear);
        List<Integer> levelArray = levelArray(scoreList);
        return R.ok().put("levelArray", levelArray);
    }

    // 条形
    private List<Integer> levelArray(List<Integer> scoreList) {
        List<Integer> list = new ArrayList<Integer>();
        int countA = 0, countB = 0, countC = 0, countD = 0, countE = 0;
        for (Integer score : scoreList) {
            if (score >= 90 && score < 100) {
                ++countA;
            } else if (score >= 80 && score < 90) {
                ++countB;
            } else if (score >= 70 && score < 80) {
                ++countC;
            } else if (score >= 60 && score < 70) {
                ++countD;
            } else {
                ++countE;
            }
        }
        list.add(countA);
        list.add(countB);
        list.add(countC);
        list.add(countD);
        list.add(countE);
        return list;
    }

    // 圆饼
    private List<EchartsVo> levelList(List<Integer> scoreList) {
        List<EchartsVo> list = new ArrayList<EchartsVo>();
        int total = scoreList.size();
        int countA = 0, countB = 0, countC = 0, countD = 0, countE = 0;
        for (Integer score : scoreList) {
            if (score >= 90 && score < 100) {
                ++countA;
            } else if (score >= 80 && score < 90) {
                ++countB;
            } else if (score >= 70 && score < 80) {
                ++countC;
            } else if (score >= 60 && score < 70) {
                ++countD;
            } else {
                ++countE;
            }
        }
        new BigDecimal((float) countA / total).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        list.add(new EchartsVo("优秀", floatOnlyTwo((float) countA / total)));
        list.add(new EchartsVo("良", floatOnlyTwo((float) countB / total)));
        list.add(new EchartsVo("中", floatOnlyTwo((float) countC / total)));
        list.add(new EchartsVo("及格", floatOnlyTwo((float) countD / total)));
        list.add(new EchartsVo("不及格", floatOnlyTwo((float) countE / total)));
        return list;
    }

    private float floatOnlyTwo(float target) {
        return BigDecimal.valueOf(target).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();

    }
}

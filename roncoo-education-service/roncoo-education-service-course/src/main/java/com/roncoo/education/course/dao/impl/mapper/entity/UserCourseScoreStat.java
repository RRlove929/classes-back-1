package com.roncoo.education.course.dao.impl.mapper.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 课程评分统计（均分、人数）
 */
public class UserCourseScoreStat implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 平均分(1-5)，保留1位小数
     */
    private BigDecimal scoreAvg;

    /**
     * 评分人数
     */
    private Integer scoreCount;

    public BigDecimal getScoreAvg() {
        return scoreAvg;
    }

    public void setScoreAvg(BigDecimal scoreAvg) {
        this.scoreAvg = scoreAvg;
    }

    public Integer getScoreCount() {
        return scoreCount;
    }

    public void setScoreCount(Integer scoreCount) {
        this.scoreCount = scoreCount;
    }
}


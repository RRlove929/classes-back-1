package com.roncoo.education.course.dao.impl.mapper.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 内容评分聚合统计（均分、人数、分布）
 */
public class ContentRatingAggStat implements Serializable {

    private static final long serialVersionUID = 1L;

    private BigDecimal scoreAvg;

    private Integer scoreCount;

    private Integer score1Count;

    private Integer score2Count;

    private Integer score3Count;

    private Integer score4Count;

    private Integer score5Count;

    private Long lastBlockNumber;

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

    public Integer getScore1Count() {
        return score1Count;
    }

    public void setScore1Count(Integer score1Count) {
        this.score1Count = score1Count;
    }

    public Integer getScore2Count() {
        return score2Count;
    }

    public void setScore2Count(Integer score2Count) {
        this.score2Count = score2Count;
    }

    public Integer getScore3Count() {
        return score3Count;
    }

    public void setScore3Count(Integer score3Count) {
        this.score3Count = score3Count;
    }

    public Integer getScore4Count() {
        return score4Count;
    }

    public void setScore4Count(Integer score4Count) {
        this.score4Count = score4Count;
    }

    public Integer getScore5Count() {
        return score5Count;
    }

    public void setScore5Count(Integer score5Count) {
        this.score5Count = score5Count;
    }

    public Long getLastBlockNumber() {
        return lastBlockNumber;
    }

    public void setLastBlockNumber(Long lastBlockNumber) {
        this.lastBlockNumber = lastBlockNumber;
    }
}


package com.roncoo.education.course.dao;

import com.roncoo.education.course.dao.impl.mapper.entity.ContentRatingAggStat;

/**
 * 内容评分聚合
 */
public interface ContentRatingAggDao {

    /**
     * 查询聚合统计（含均分/人数/分布）
     */
    ContentRatingAggStat statByContentId(String contentType, Long contentId);

    /**
     * 增量聚合：插入明细后调用该方法累加聚合结果
     */
    int incrByScore(String contentType, Long contentId, Integer score, Long lastBlockNumber);
}


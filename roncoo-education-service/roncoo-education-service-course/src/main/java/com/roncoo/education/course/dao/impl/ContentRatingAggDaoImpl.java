package com.roncoo.education.course.dao.impl;

import com.roncoo.education.common.core.tools.IdWorker;
import com.roncoo.education.course.dao.ContentRatingAggDao;
import com.roncoo.education.course.dao.impl.mapper.ContentRatingAggMapper;
import com.roncoo.education.course.dao.impl.mapper.entity.ContentRatingAggStat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;

/**
 * 内容评分聚合 服务实现类
 */
@Repository
@RequiredArgsConstructor
public class ContentRatingAggDaoImpl implements ContentRatingAggDao {

    @NotNull
    private final ContentRatingAggMapper mapper;

    @Override
    public ContentRatingAggStat statByContentId(String contentType, Long contentId) {
        return this.mapper.selectStatByContentId(contentType, contentId);
    }

    @Override
    public int incrByScore(String contentType, Long contentId, Integer score, Long lastBlockNumber) {
        return this.mapper.upsertIncrByScore(IdWorker.getId(), contentType, contentId, score, lastBlockNumber);
    }
}


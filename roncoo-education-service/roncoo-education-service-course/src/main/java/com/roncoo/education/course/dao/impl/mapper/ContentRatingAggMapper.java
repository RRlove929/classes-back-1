package com.roncoo.education.course.dao.impl.mapper;

import com.roncoo.education.course.dao.impl.mapper.entity.ContentRatingAggStat;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ContentRatingAggMapper {

    ContentRatingAggStat selectStatByContentId(@Param("contentType") String contentType, @Param("contentId") Long contentId);

    int upsertIncrByScore(@Param("id") Long id,
                          @Param("contentType") String contentType,
                          @Param("contentId") Long contentId,
                          @Param("score") Integer score,
                          @Param("lastBlockNumber") Long lastBlockNumber);
}


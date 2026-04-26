package com.roncoo.education.course.dao.impl.mapper;

import com.roncoo.education.course.dao.impl.mapper.entity.ChainComment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ChainCommentMapper {

    int insertSelective(ChainComment record);

    List<ChainComment> selectByContentIdForPage(@Param("contentType") String contentType,
                                                @Param("contentId") Long contentId,
                                                @Param("offset") Long offset,
                                                @Param("pageSize") Integer pageSize);

    Integer countByContentId(@Param("contentType") String contentType, @Param("contentId") Long contentId);

    List<ChainComment> selectPendingMeta(@Param("limit") Integer limit);

    int updateChainMetaById(@Param("id") Long id,
                            @Param("chainId") Long chainId,
                            @Param("blockNumber") Long blockNumber,
                            @Param("logIndex") Integer logIndex);
}


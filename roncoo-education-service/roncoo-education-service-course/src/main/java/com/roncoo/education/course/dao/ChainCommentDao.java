package com.roncoo.education.course.dao;

import com.roncoo.education.course.dao.impl.mapper.entity.ChainComment;

import java.util.List;

/**
 * 链上评价明细
 */
public interface ChainCommentDao {

    int save(ChainComment record);

    /**
     * 根据内容分页查询（按区块高度/时间倒序）
     */
    List<ChainComment> listByContentIdForPage(String contentType, Long contentId, Long offset, Integer pageSize);

    /**
     * 根据内容统计数量
     */
    Integer countByContentId(String contentType, Long contentId);

    /**
     * 查询待回填链元数据的记录（block_number=0 且 tx_hash 非空）
     */
    List<ChainComment> listPendingMeta(Integer limit);

    /**
     * 更新链元数据（区块高度、日志序号、链ID）
     */
    int updateChainMetaById(Long id, Long chainId, Long blockNumber, Integer logIndex);
}


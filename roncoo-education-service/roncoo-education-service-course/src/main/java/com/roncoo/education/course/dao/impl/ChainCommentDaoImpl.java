package com.roncoo.education.course.dao.impl;

import com.roncoo.education.common.core.tools.IdWorker;
import com.roncoo.education.course.dao.ChainCommentDao;
import com.roncoo.education.course.dao.impl.mapper.ChainCommentMapper;
import com.roncoo.education.course.dao.impl.mapper.entity.ChainComment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 链上评价明细 服务实现类
 */
@Repository
@RequiredArgsConstructor
public class ChainCommentDaoImpl implements ChainCommentDao {

    @NotNull
    private final ChainCommentMapper mapper;

    @Override
    public int save(ChainComment record) {
        if (record.getId() == null) {
            record.setId(IdWorker.getId());
        }
        return this.mapper.insertSelective(record);
    }

    @Override
    public List<ChainComment> listByContentIdForPage(String contentType, Long contentId, Long offset, Integer pageSize) {
        return this.mapper.selectByContentIdForPage(contentType, contentId, offset, pageSize);
    }

    @Override
    public Integer countByContentId(String contentType, Long contentId) {
        return this.mapper.countByContentId(contentType, contentId);
    }

    @Override
    public List<ChainComment> listPendingMeta(Integer limit) {
        return this.mapper.selectPendingMeta(limit);
    }

    @Override
    public int updateChainMetaById(Long id, Long chainId, Long blockNumber, Integer logIndex) {
        return this.mapper.updateChainMetaById(id, chainId, blockNumber, logIndex);
    }
}


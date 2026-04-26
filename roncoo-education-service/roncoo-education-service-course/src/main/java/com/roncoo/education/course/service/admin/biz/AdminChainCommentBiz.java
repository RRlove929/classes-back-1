package com.roncoo.education.course.service.admin.biz;

import com.roncoo.education.common.core.base.Result;
import com.roncoo.education.course.dao.ChainCommentDao;
import com.roncoo.education.course.dao.ContentRatingAggDao;
import com.roncoo.education.course.dao.impl.mapper.entity.ChainComment;
import com.roncoo.education.course.service.admin.req.AdminChainCommentIngestReq;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * ADMIN-链上评价（接收索引器推送/人工导入）
 */
@Component
@RequiredArgsConstructor
public class AdminChainCommentBiz {

    private static final String DEFAULT_CONTENT_TYPE = "course";

    @NotNull
    private final ChainCommentDao chainCommentDao;

    @NotNull
    private final ContentRatingAggDao contentRatingAggDao;

    public Result<Integer> ingest(AdminChainCommentIngestReq req) {
        if (req == null || req.getItems() == null || req.getItems().isEmpty()) {
            return Result.success(0);
        }
        int success = 0;
        for (AdminChainCommentIngestReq.Item item : req.getItems()) {
            ChainComment cc = new ChainComment();
            cc.setChainId(item.getChainId());
            cc.setContractAddress(item.getContractAddress());
            cc.setContentType(item.getContentType() == null ? DEFAULT_CONTENT_TYPE : item.getContentType());
            cc.setContentId(item.getContentId());
            cc.setUserAddress(item.getUserAddress());
            cc.setScore(item.getScore());
            cc.setCommentUri(item.getCommentUri());
            cc.setCommentHash(item.getCommentHash());
            cc.setTxHash(item.getTxHash());
            cc.setBlockNumber(item.getBlockNumber());
            cc.setLogIndex(item.getLogIndex());
            cc.setEventTime(item.getEventTime());

            try {
                chainCommentDao.save(cc);
            } catch (DuplicateKeyException ignore) {
                // 重复推送（同 txHash+logIndex）直接忽略
                continue;
            }

            // 仅当 score=1..5 时参与聚合
            if (cc.getScore() != null && cc.getScore() >= 1 && cc.getScore() <= 5) {
                contentRatingAggDao.incrByScore(cc.getContentType(), cc.getContentId(), cc.getScore(), cc.getBlockNumber());
            }
            success++;
        }
        return Result.success(success);
    }
}


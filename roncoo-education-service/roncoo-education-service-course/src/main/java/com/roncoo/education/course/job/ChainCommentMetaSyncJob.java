package com.roncoo.education.course.job;

import cn.hutool.core.collection.CollUtil;
import com.roncoo.education.course.dao.ChainCommentDao;
import com.roncoo.education.course.dao.impl.mapper.entity.ChainComment;
import com.roncoo.education.course.fabric.FabricMetaQuery;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 链上评价元数据回填任务
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ChainCommentMetaSyncJob {

    @NotNull
    private final ChainCommentDao chainCommentDao;
    @NotNull
    private final FabricMetaQuery fabricMetaQuery;

    /**
     * 建议：每30秒~1分钟执行一次
     * 入参可选：limit（如 100）
     */
    @XxlJob("chainCommentMetaSyncJobHandler")
    public void sync() {
        int limit = 100;
        try {
            String param = XxlJobHelper.getJobParam();
            if (param != null && !param.trim().isEmpty()) {
                limit = Integer.parseInt(param.trim());
            }
        } catch (Exception ignored) {
        }
        List<ChainComment> pendingList = chainCommentDao.listPendingMeta(limit);
        if (CollUtil.isEmpty(pendingList)) {
            XxlJobHelper.handleSuccess("no pending chain comment");
            return;
        }
        int success = 0;
        for (ChainComment item : pendingList) {
            Long blockNumber = fabricMetaQuery.queryBlockNumberByTxId(item.getTxHash());
            if (blockNumber == null || blockNumber <= 0) {
                continue;
            }
            int rows = chainCommentDao.updateChainMetaById(item.getId(), 1L, blockNumber, 0);
            if (rows > 0) {
                success++;
            }
        }
        XxlJobHelper.handleSuccess("pending=" + pendingList.size() + ", success=" + success);
        log.info("chain comment meta sync done, pending={}, success={}", pendingList.size(), success);
    }
}


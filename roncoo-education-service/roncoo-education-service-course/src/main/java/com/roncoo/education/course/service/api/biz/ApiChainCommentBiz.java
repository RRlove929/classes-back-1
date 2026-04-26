package com.roncoo.education.course.service.api.biz;

import com.roncoo.education.common.core.base.Page;
import com.roncoo.education.common.core.base.Result;
import com.roncoo.education.common.core.tools.BeanUtil;
import com.roncoo.education.course.dao.ChainCommentDao;
import com.roncoo.education.course.dao.ContentRatingAggDao;
import com.roncoo.education.course.dao.impl.mapper.entity.ChainComment;
import com.roncoo.education.course.dao.impl.mapper.entity.ContentRatingAggStat;
import com.roncoo.education.course.service.api.resp.ApiChainCommentResp;
import com.roncoo.education.course.service.api.resp.ApiContentRatingAggResp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * API-链上评价公开查询
 */
@Component
@RequiredArgsConstructor
public class ApiChainCommentBiz {

    private static final String DEFAULT_CONTENT_TYPE = "course";

    @NotNull
    private final ChainCommentDao chainCommentDao;

    @NotNull
    private final ContentRatingAggDao contentRatingAggDao;

    public Result<ApiContentRatingAggResp> agg(String contentType, Long contentId) {
        String type = contentType == null ? DEFAULT_CONTENT_TYPE : contentType;
        ContentRatingAggStat stat = contentRatingAggDao.statByContentId(type, contentId);
        ApiContentRatingAggResp resp = new ApiContentRatingAggResp();
        resp.setContentType(type);
        resp.setContentId(contentId);
        if (stat != null) {
            resp.setScoreAvg(stat.getScoreAvg());
            resp.setScoreCount(stat.getScoreCount());
            resp.setScore1Count(stat.getScore1Count());
            resp.setScore2Count(stat.getScore2Count());
            resp.setScore3Count(stat.getScore3Count());
            resp.setScore4Count(stat.getScore4Count());
            resp.setScore5Count(stat.getScore5Count());
            resp.setLastBlockNumber(stat.getLastBlockNumber());
        } else {
            resp.setScoreCount(0);
            resp.setScore1Count(0);
            resp.setScore2Count(0);
            resp.setScore3Count(0);
            resp.setScore4Count(0);
            resp.setScore5Count(0);
        }
        return Result.success(resp);
    }

    public Result<Page<ApiChainCommentResp>> page(String contentType, Long contentId, Integer pageCurrent, Integer pageSize) {
        String type = contentType == null ? DEFAULT_CONTENT_TYPE : contentType;
        int size = pageSize == null ? 10 : pageSize;
        int current = pageCurrent == null ? 1 : pageCurrent;
        long offset = (long) (current - 1) * size;

        Integer total = chainCommentDao.countByContentId(type, contentId);
        List<ChainComment> list = chainCommentDao.listByContentIdForPage(type, contentId, offset, size);
        List<ApiChainCommentResp> respList = BeanUtil.copyProperties(list, ApiChainCommentResp.class);

        Page<ApiChainCommentResp> page = new Page<>();
        page.setPageCurrent(current);
        page.setPageSize(size);
        page.setTotalCount(total == null ? 0 : total);
        page.setList(respList);
        return Result.success(page);
    }
}


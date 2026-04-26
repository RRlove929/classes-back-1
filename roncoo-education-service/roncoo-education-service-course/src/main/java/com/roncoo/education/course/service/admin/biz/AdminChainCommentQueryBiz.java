package com.roncoo.education.course.service.admin.biz;

import com.roncoo.education.common.core.base.Page;
import com.roncoo.education.common.core.base.Result;
import com.roncoo.education.course.service.admin.req.AdminChainCommentAggReq;
import com.roncoo.education.course.service.admin.req.AdminChainCommentPageReq;
import com.roncoo.education.course.service.api.biz.ApiChainCommentBiz;
import com.roncoo.education.course.service.api.resp.ApiChainCommentResp;
import com.roncoo.education.course.service.api.resp.ApiContentRatingAggResp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * ADMIN-链上评价查询
 */
@Component
@RequiredArgsConstructor
public class AdminChainCommentQueryBiz {

    @NotNull
    private final ApiChainCommentBiz apiChainCommentBiz;

    public Result<ApiContentRatingAggResp> agg(AdminChainCommentAggReq req) {
        return apiChainCommentBiz.agg(req.getContentType(), req.getContentId());
    }

    public Result<Page<ApiChainCommentResp>> page(AdminChainCommentPageReq req) {
        return apiChainCommentBiz.page(req.getContentType(), req.getContentId(), req.getPageCurrent(), req.getPageSize());
    }
}


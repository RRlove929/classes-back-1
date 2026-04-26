package com.roncoo.education.course.service.api;

import com.roncoo.education.common.core.base.Page;
import com.roncoo.education.common.core.base.Result;
import com.roncoo.education.course.service.api.biz.ApiChainCommentBiz;
import com.roncoo.education.course.service.api.resp.ApiChainCommentResp;
import com.roncoo.education.course.service.api.resp.ApiContentRatingAggResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * API-链上评价（公开可查）
 */
@Api(tags = "api-链上评价")
@RestController
@RequiredArgsConstructor
@RequestMapping("/course/api/chain-comment")
public class ApiChainCommentController {

    @NotNull
    private final ApiChainCommentBiz biz;

    @ApiOperation(value = "内容评分聚合查询", notes = "返回综合评分与分布统计")
    @ApiImplicitParam(name = "contentId", value = "内容ID(课程ID)", dataTypeClass = Long.class, paramType = "query", required = true)
    @GetMapping(value = "/agg")
    public Result<ApiContentRatingAggResp> agg(@RequestParam(required = false) String contentType, @RequestParam Long contentId) {
        return biz.agg(contentType, contentId);
    }

    @ApiOperation(value = "链上评价明细分页", notes = "公开可查，含txHash/blockNumber/logIndex等可校验字段")
    @GetMapping(value = "/page")
    public Result<Page<ApiChainCommentResp>> page(@RequestParam(required = false) String contentType,
                                                 @RequestParam Long contentId,
                                                 @RequestParam(required = false) Integer pageCurrent,
                                                 @RequestParam(required = false) Integer pageSize) {
        return biz.page(contentType, contentId, pageCurrent, pageSize);
    }
}


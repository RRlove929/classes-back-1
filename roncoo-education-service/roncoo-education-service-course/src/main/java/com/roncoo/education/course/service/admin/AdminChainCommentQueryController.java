package com.roncoo.education.course.service.admin;

import com.roncoo.education.common.core.base.Page;
import com.roncoo.education.common.core.base.Result;
import com.roncoo.education.course.service.admin.biz.AdminChainCommentQueryBiz;
import com.roncoo.education.course.service.admin.req.AdminChainCommentAggReq;
import com.roncoo.education.course.service.admin.req.AdminChainCommentPageReq;
import com.roncoo.education.course.service.api.resp.ApiChainCommentResp;
import com.roncoo.education.course.service.api.resp.ApiContentRatingAggResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * ADMIN-链上评价查询（适配管理端调用路径）
 */
@Api(tags = "admin-链上评价查询")
@RestController
@RequiredArgsConstructor
@RequestMapping("/course/admin/chain/comment")
public class AdminChainCommentQueryController {

    @NotNull
    private final AdminChainCommentQueryBiz biz;

    @ApiOperation(value = "聚合评分查询", notes = "按课程ID查询综合评分与分布")
    @PostMapping(value = "/agg")
    public Result<ApiContentRatingAggResp> agg(@RequestBody @Valid AdminChainCommentAggReq req) {
        return biz.agg(req);
    }

    @ApiOperation(value = "链上评价明细分页", notes = "按课程ID分页查询链上评价明细")
    @PostMapping(value = "/page")
    public Result<Page<ApiChainCommentResp>> page(@RequestBody @Valid AdminChainCommentPageReq req) {
        return biz.page(req);
    }
}


package com.roncoo.education.course.service.admin;

import com.roncoo.education.common.annotation.SysLog;
import com.roncoo.education.common.core.base.Result;
import com.roncoo.education.course.service.admin.biz.AdminChainCommentBiz;
import com.roncoo.education.course.service.admin.req.AdminChainCommentIngestReq;
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
 * ADMIN-链上评价
 */
@Api(tags = "admin-链上评价")
@RestController
@RequiredArgsConstructor
@RequestMapping("/course/admin/chain-comment")
public class AdminChainCommentController {

    @NotNull
    private final AdminChainCommentBiz biz;

    @ApiOperation(value = "批量导入/推送链上评价", notes = "由链上索引器推送进来，后端负责落库并增量更新聚合表")
    @SysLog(value = "链上评价导入")
    @PostMapping(value = "/ingest")
    public Result<Integer> ingest(@RequestBody @Valid AdminChainCommentIngestReq req) {
        return biz.ingest(req);
    }
}


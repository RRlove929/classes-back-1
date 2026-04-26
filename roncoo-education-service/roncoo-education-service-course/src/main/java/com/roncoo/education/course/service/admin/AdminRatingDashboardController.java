package com.roncoo.education.course.service.admin;

import com.roncoo.education.common.core.base.Result;
import com.roncoo.education.course.service.admin.biz.AdminRatingDashboardBiz;
import com.roncoo.education.course.service.admin.req.AdminRatingDashboardReq;
import com.roncoo.education.course.service.admin.resp.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@Api(tags = "admin-评价数据看板")
@RestController
@RequiredArgsConstructor
@RequestMapping("/course/admin/dashboard/rating")
public class AdminRatingDashboardController {

    @NotNull
    private final AdminRatingDashboardBiz biz;

    @ApiOperation(value = "概览", notes = "总评价、均分、分布等")
    @PostMapping("/overview")
    public Result<AdminRatingDashboardOverviewResp> overview(@RequestBody AdminRatingDashboardReq req) {
        return biz.overview(req);
    }

    @ApiOperation(value = "评分趋势", notes = "按日统计评分数和均分")
    @PostMapping("/trend")
    public Result<List<AdminRatingTrendResp>> trend(@RequestBody AdminRatingDashboardReq req) {
        return biz.trend(req);
    }

    @ApiOperation(value = "课程排行", notes = "按均分和评分人数排序")
    @PostMapping("/course-rank")
    public Result<List<AdminRatingCourseRankResp>> courseRank(@RequestBody AdminRatingDashboardReq req) {
        return biz.courseRank(req);
    }

    @ApiOperation(value = "高频关键词", notes = "基于评论文本的关键词TopN")
    @PostMapping("/keywords")
    public Result<List<AdminKeywordResp>> keywords(@RequestBody AdminRatingDashboardReq req) {
        return biz.keywords(req);
    }
}


package com.roncoo.education.course.service.auth;

import com.roncoo.education.common.core.base.Result;
import com.roncoo.education.course.service.auth.biz.AuthUserCourseScoreBiz;
import com.roncoo.education.course.service.auth.req.AuthUserCourseScoreReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * AUTH-课程评分
 */
@Api(tags = "auth-课程评分")
@RestController
@RequiredArgsConstructor
@RequestMapping("/course/auth/user/course/score")
public class AuthUserCourseScoreController {

    @NotNull
    private final AuthUserCourseScoreBiz biz;

    @ApiOperation(value = "课程评分", notes = "每个用户每个课程只能评分一次")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<String> add(@RequestBody @Valid AuthUserCourseScoreReq req) {
        return biz.add(req);
    }
}


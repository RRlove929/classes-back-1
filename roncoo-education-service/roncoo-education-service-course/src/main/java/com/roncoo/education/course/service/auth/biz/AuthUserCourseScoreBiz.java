package com.roncoo.education.course.service.auth.biz;

import cn.hutool.core.util.ObjectUtil;
import com.roncoo.education.common.config.ThreadContext;
import com.roncoo.education.common.core.base.Result;
import com.roncoo.education.common.core.tools.BeanUtil;
import com.roncoo.education.common.core.tools.JsonUtil;
import com.roncoo.education.common.service.BaseBiz;
import com.roncoo.education.course.dao.UserCourseDao;
import com.roncoo.education.course.dao.UserCourseScoreDao;
import com.roncoo.education.course.dao.impl.mapper.entity.UserCourse;
import com.roncoo.education.course.dao.impl.mapper.entity.UserCourseScore;
import com.roncoo.education.course.fabric.FabricContract;
import com.roncoo.education.course.service.auth.req.AuthUserCourseScoreReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;

/**
 * AUTH-课程评分
 */
@Component
@RequiredArgsConstructor
public class AuthUserCourseScoreBiz extends BaseBiz {

    @NotNull
    private final UserCourseScoreDao scoreDao;

    @NotNull
    private final UserCourseDao userCourseDao;

    private final FabricContract fabricContract;

    /**
     * 评分（每个用户每个课程只能评分一次）
     */
    @Transactional
    public Result<String> add(AuthUserCourseScoreReq req) {
        Long userId = ThreadContext.userId();
        Long courseId = req.getCourseId();

        // 购买校验：与评论保持一致（只有购买才能评分）
        UserCourse userCourse = userCourseDao.getByCourseIdAndUserId(courseId, userId);
        if (userCourse == null) {
            return Result.error("只有购买该课程的用户才能评分");
        }

        // 是否已评过分：只允许一次
        UserCourseScore old = scoreDao.getByCourseIdAndUserId(courseId, userId);
        if (ObjectUtil.isNotEmpty(old)) {
            return Result.error("您已对该课程评过分，不能重复评分");
        }

        UserCourseScore score = BeanUtil.copyProperties(req, UserCourseScore.class);
        score.setUserId(userId);
        scoreDao.save(score);

        // 上链保存（与评论保持一致）
        String key = "score:" + score.getId();
        String txHash = fabricContract.createBizRecord(key, JsonUtil.toJsonString(score));
        log.info("评分上链：key[{}], txHash[{}]", key, txHash);

        return Result.success("评分成功");
    }
}


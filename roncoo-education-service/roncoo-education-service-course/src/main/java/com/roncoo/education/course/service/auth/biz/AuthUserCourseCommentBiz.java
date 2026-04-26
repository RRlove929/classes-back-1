package com.roncoo.education.course.service.auth.biz;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.roncoo.education.common.config.ThreadContext;
import com.roncoo.education.common.core.base.Page;
import com.roncoo.education.common.core.base.PageUtil;
import com.roncoo.education.common.core.base.Result;
import com.roncoo.education.common.core.tools.BeanUtil;
import com.roncoo.education.common.core.tools.JsonUtil;
import com.roncoo.education.common.service.BaseBiz;
import com.roncoo.education.course.dao.ChainCommentDao;
import com.roncoo.education.course.dao.CourseDao;
import com.roncoo.education.course.dao.UserCourseCommentDao;
import com.roncoo.education.course.dao.UserCourseDao;
import com.roncoo.education.course.dao.impl.mapper.entity.ChainComment;
import com.roncoo.education.course.dao.impl.mapper.entity.Course;
import com.roncoo.education.course.dao.impl.mapper.entity.UserCourse;
import com.roncoo.education.course.dao.impl.mapper.entity.UserCourseComment;
import com.roncoo.education.course.dao.impl.mapper.entity.UserCourseCommentExample;
import com.roncoo.education.course.fabric.FabricContract;
import com.roncoo.education.course.service.auth.req.AuthUserCourseCommentPageReq;
import com.roncoo.education.course.service.auth.req.AuthUserCourseCommentReq;
import com.roncoo.education.course.service.auth.resp.AuthUserCourseCommentResp;
import com.roncoo.education.course.service.biz.resp.CourseResp;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * AUTH-课程评论
 *
 * @author wujing
 */
@Component
@RequiredArgsConstructor
public class AuthUserCourseCommentBiz extends BaseBiz {

    @NotNull
    private final UserCourseCommentDao dao;
    @NotNull
    private final CourseDao courseDao;

    @NotNull
    private final UserCourseDao userCourseDao;

    @NotNull
    private final ChainCommentDao chainCommentDao;

    private final FabricContract fabricContract;

    public Result<Page<AuthUserCourseCommentResp>> listForPage(AuthUserCourseCommentPageReq req) {
        UserCourseCommentExample example = new UserCourseCommentExample();
        example.createCriteria().andUserIdEqualTo(ThreadContext.userId());
        example.setOrderByClause("id desc");

        Page<AuthUserCourseCommentResp> resp = PageUtil.transform(dao.page(req.getPageCurrent(), req.getPageSize(), example), AuthUserCourseCommentResp.class);
        if (CollUtil.isNotEmpty(resp.getList())) {
            List<Long> courseIds = resp.getList().stream().map(AuthUserCourseCommentResp::getCourseId).collect(Collectors.toList());
            Map<Long, Course> courseMap = courseDao.listByIds(courseIds).stream().collect(Collectors.toMap(Course::getId, item -> item));
            for (AuthUserCourseCommentResp res : resp.getList()) {
                res.setCourseResp(BeanUtil.copyProperties(courseMap.get(res.getCourseId()), CourseResp.class));
            }
        }
        return Result.success(resp);
    }

    @Transactional
    public Result<String> add(AuthUserCourseCommentReq req) {
        Long userId = ThreadContext.userId();
        Long courseId = req.getCourseId();

        // ==============================================
        // 【核心】判断用户是否购买了该课程
        // ==============================================
        UserCourse userCourse = userCourseDao.getByCourseIdAndUserId(courseId, userId);
        if (userCourse == null) {
            // 没买 → 直接不让评论
            return Result.error("只有购买该课程的用户才能评论");
        }

        // 购买了 → 才允许保存评论
        UserCourseComment userCourseComment = BeanUtil.copyProperties(req, UserCourseComment.class);
        userCourseComment.setUserId(userId);
        dao.save(userCourseComment);

        // 上链保存
        String key = "comment:" + userCourseComment.getId();
        String txHash = fabricContract.createBizRecord(key, JsonUtil.toJsonString(userCourseComment));
        log.info("评论上链：key[{}], txHash[{}]", key, txHash);

        // 同步写入“链上评价明细表”（评论本身不参与评分聚合，score=0）
        ChainComment cc = new ChainComment();
        cc.setChainId(0L);
        cc.setContractAddress("");
        cc.setContentType("course");
        cc.setContentId(courseId);
        cc.setUserAddress("uid:" + userId);
        cc.setScore(0);
        cc.setCommentUri("");
        cc.setCommentHash(DigestUtil.sha256Hex(userCourseComment.getCommentText()));
        cc.setTxHash(txHash);
        cc.setBlockNumber(0L);
        cc.setLogIndex(0);
        cc.setEventTime(0L);
        try {
            chainCommentDao.save(cc);
        } catch (DuplicateKeyException ignore) {
        }

        return Result.success("评论成功");
    }

}
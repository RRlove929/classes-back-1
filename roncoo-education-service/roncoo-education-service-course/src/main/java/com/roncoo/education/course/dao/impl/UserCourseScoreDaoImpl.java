package com.roncoo.education.course.dao.impl;

import com.roncoo.education.common.core.tools.IdWorker;
import com.roncoo.education.course.dao.UserCourseScoreDao;
import com.roncoo.education.course.dao.impl.mapper.UserCourseScoreMapper;
import com.roncoo.education.course.dao.impl.mapper.entity.UserCourseScore;
import com.roncoo.education.course.dao.impl.mapper.entity.UserCourseScoreStat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;

/**
 * 用户课程评分 服务实现类
 */
@Repository
@RequiredArgsConstructor
public class UserCourseScoreDaoImpl implements UserCourseScoreDao {

    @NotNull
    private final UserCourseScoreMapper mapper;

    @Override
    public int save(UserCourseScore record) {
        if (record.getId() == null) {
            record.setId(IdWorker.getId());
        }
        return this.mapper.insertSelective(record);
    }

    @Override
    public UserCourseScore getByCourseIdAndUserId(Long courseId, Long userId) {
        return this.mapper.selectByCourseIdAndUserId(courseId, userId);
    }

    @Override
    public UserCourseScoreStat statByCourseId(Long courseId) {
        return this.mapper.selectStatByCourseId(courseId);
    }
}


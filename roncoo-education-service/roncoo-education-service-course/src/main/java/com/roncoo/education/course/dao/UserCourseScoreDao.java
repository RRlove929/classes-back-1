package com.roncoo.education.course.dao;

import com.roncoo.education.course.dao.impl.mapper.entity.UserCourseScore;
import com.roncoo.education.course.dao.impl.mapper.entity.UserCourseScoreStat;

/**
 * 用户课程评分
 */
public interface UserCourseScoreDao {

    int save(UserCourseScore record);

    UserCourseScore getByCourseIdAndUserId(Long courseId, Long userId);

    UserCourseScoreStat statByCourseId(Long courseId);
}


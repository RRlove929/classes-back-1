package com.roncoo.education.course.dao.impl.mapper;

import com.roncoo.education.course.dao.impl.mapper.entity.UserCourseScore;
import com.roncoo.education.course.dao.impl.mapper.entity.UserCourseScoreStat;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserCourseScoreMapper {

    int insertSelective(UserCourseScore record);

    UserCourseScore selectByCourseIdAndUserId(@Param("courseId") Long courseId, @Param("userId") Long userId);

    UserCourseScoreStat selectStatByCourseId(@Param("courseId") Long courseId);
}


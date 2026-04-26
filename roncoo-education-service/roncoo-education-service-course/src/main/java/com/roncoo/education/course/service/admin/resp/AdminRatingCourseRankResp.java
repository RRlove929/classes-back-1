package com.roncoo.education.course.service.admin.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(description = "ADMIN-课程评分排行")
public class AdminRatingCourseRankResp implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程ID")
    private Long courseId;

    @ApiModelProperty(value = "课程名")
    private String courseName;

    @ApiModelProperty(value = "评分人数")
    private Integer scoreCount;

    @ApiModelProperty(value = "平均分")
    private BigDecimal scoreAvg;
}


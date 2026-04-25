package com.roncoo.education.course.service.auth.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * AUTH-课程评分
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "AUTH-课程评分请求")
public class AuthUserCourseScoreReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @ApiModelProperty(value = "课程ID", required = true)
    private Long courseId;

    @NotNull
    @Min(1)
    @Max(5)
    @ApiModelProperty(value = "评分(1-5)", required = true)
    private Integer score;
}


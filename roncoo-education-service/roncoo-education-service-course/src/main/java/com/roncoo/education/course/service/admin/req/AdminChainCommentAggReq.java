package com.roncoo.education.course.service.admin.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel(description = "ADMIN-链上评价聚合查询")
public class AdminChainCommentAggReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "内容类型，默认course")
    private String contentType;

    @NotNull
    @ApiModelProperty(value = "内容ID(课程ID)", required = true)
    private Long contentId;
}


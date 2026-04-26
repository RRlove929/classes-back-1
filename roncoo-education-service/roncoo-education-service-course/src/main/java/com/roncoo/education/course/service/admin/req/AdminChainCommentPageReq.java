package com.roncoo.education.course.service.admin.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel(description = "ADMIN-链上评价明细分页")
public class AdminChainCommentPageReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "内容类型，默认course")
    private String contentType;

    @NotNull
    @ApiModelProperty(value = "内容ID(课程ID)", required = true)
    private Long contentId;

    @ApiModelProperty(value = "当前页，默认1")
    private Integer pageCurrent = 1;

    @ApiModelProperty(value = "每页大小，默认20")
    private Integer pageSize = 20;
}


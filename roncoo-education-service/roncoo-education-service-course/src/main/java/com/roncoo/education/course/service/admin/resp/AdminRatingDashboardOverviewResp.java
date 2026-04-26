package com.roncoo.education.course.service.admin.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(description = "ADMIN-评价看板概览")
public class AdminRatingDashboardOverviewResp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程数")
    private Integer courseCount;

    @ApiModelProperty(value = "评价总数")
    private Integer scoreCount;

    @ApiModelProperty(value = "综合均分")
    private BigDecimal scoreAvg;

    @ApiModelProperty(value = "1星数量")
    private Integer score1Count;
    @ApiModelProperty(value = "2星数量")
    private Integer score2Count;
    @ApiModelProperty(value = "3星数量")
    private Integer score3Count;
    @ApiModelProperty(value = "4星数量")
    private Integer score4Count;
    @ApiModelProperty(value = "5星数量")
    private Integer score5Count;
}


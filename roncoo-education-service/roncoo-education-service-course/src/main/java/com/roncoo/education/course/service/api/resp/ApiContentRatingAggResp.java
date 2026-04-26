package com.roncoo.education.course.service.api.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(description = "API-内容评分聚合")
public class ApiContentRatingAggResp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "内容类型")
    private String contentType;

    @ApiModelProperty(value = "内容ID")
    private Long contentId;

    @ApiModelProperty(value = "平均分(1-5)")
    private BigDecimal scoreAvg;

    @ApiModelProperty(value = "评分人数")
    private Integer scoreCount;

    @ApiModelProperty(value = "1分数量")
    private Integer score1Count;

    @ApiModelProperty(value = "2分数量")
    private Integer score2Count;

    @ApiModelProperty(value = "3分数量")
    private Integer score3Count;

    @ApiModelProperty(value = "4分数量")
    private Integer score4Count;

    @ApiModelProperty(value = "5分数量")
    private Integer score5Count;

    @ApiModelProperty(value = "聚合到的最新区块")
    private Long lastBlockNumber;
}


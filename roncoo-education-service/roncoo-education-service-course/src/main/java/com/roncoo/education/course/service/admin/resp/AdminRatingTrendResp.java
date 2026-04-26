package com.roncoo.education.course.service.admin.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(description = "ADMIN-评价趋势")
public class AdminRatingTrendResp implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "日期")
    private String day;

    @ApiModelProperty(value = "当日评分数")
    private Integer scoreCount;

    @ApiModelProperty(value = "当日平均分")
    private BigDecimal scoreAvg;
}


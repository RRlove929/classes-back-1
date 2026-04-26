package com.roncoo.education.course.service.admin.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "ADMIN-评价看板查询参数")
public class AdminRatingDashboardReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "讲师ID，可空")
    private Long lecturerId;

    @ApiModelProperty(value = "课程ID，可空")
    private Long courseId;

    @ApiModelProperty(value = "开始日期(yyyy-MM-dd)，可空")
    private String beginDate;

    @ApiModelProperty(value = "结束日期(yyyy-MM-dd)，可空")
    private String endDate;

    @ApiModelProperty(value = "Top数量，默认10")
    private Integer topN = 10;
}


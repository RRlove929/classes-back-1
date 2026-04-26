package com.roncoo.education.course.service.admin.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "ADMIN-关键词统计")
public class AdminKeywordResp implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "关键词")
    private String word;

    @ApiModelProperty(value = "次数")
    private Integer count;
}


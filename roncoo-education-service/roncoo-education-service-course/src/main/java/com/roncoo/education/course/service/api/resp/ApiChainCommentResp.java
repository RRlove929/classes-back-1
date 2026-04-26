package com.roncoo.education.course.service.api.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "API-链上评价明细")
public class ApiChainCommentResp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "链ID")
    private Long chainId;

    @ApiModelProperty(value = "合约地址")
    private String contractAddress;

    @ApiModelProperty(value = "内容类型")
    private String contentType;

    @ApiModelProperty(value = "内容ID")
    private Long contentId;

    @ApiModelProperty(value = "评价者地址")
    private String userAddress;

    @ApiModelProperty(value = "评分(1-5)")
    private Integer score;

    @ApiModelProperty(value = "评论URI")
    private String commentUri;

    @ApiModelProperty(value = "评论hash")
    private String commentHash;

    @ApiModelProperty(value = "交易hash")
    private String txHash;

    @ApiModelProperty(value = "区块高度")
    private Long blockNumber;

    @ApiModelProperty(value = "日志序号")
    private Integer logIndex;

    @ApiModelProperty(value = "链上事件时间戳(秒)")
    private Long eventTime;
}


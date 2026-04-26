package com.roncoo.education.course.service.admin.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "ADMIN-链上评价批量导入")
public class AdminChainCommentIngestReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "评价列表")
    private List<Item> items;

    @Data
    @ApiModel(description = "链上评价条目")
    public static class Item implements Serializable {
        private static final long serialVersionUID = 1L;

        @NotNull
        @ApiModelProperty(value = "链ID", required = true)
        private Long chainId;

        @ApiModelProperty(value = "合约地址")
        private String contractAddress;

        @ApiModelProperty(value = "内容类型，默认course")
        private String contentType;

        @NotNull
        @ApiModelProperty(value = "内容ID(课程ID)", required = true)
        private Long contentId;

        @NotBlank
        @ApiModelProperty(value = "评价者地址", required = true)
        private String userAddress;

        @ApiModelProperty(value = "评分(1-5)")
        private Integer score;

        @ApiModelProperty(value = "评论URI(IPFS/OSS)")
        private String commentUri;

        @ApiModelProperty(value = "评论hash")
        private String commentHash;

        @NotBlank
        @ApiModelProperty(value = "交易hash", required = true)
        private String txHash;

        @NotNull
        @ApiModelProperty(value = "区块高度", required = true)
        private Long blockNumber;

        @NotNull
        @ApiModelProperty(value = "日志序号", required = true)
        private Integer logIndex;

        @ApiModelProperty(value = "事件时间戳(秒)")
        private Long eventTime;
    }
}


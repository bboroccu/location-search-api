package com.kakaobank.location.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@ApiModel
@Getter @Setter
@Builder
public class PageInfo {
    @ApiModelProperty(value = "전체 카운트")
    private long totalCount;
    @ApiModelProperty(value = "전체 페이지")
    private int totalPage;
}

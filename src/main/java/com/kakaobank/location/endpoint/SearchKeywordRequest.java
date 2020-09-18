package com.kakaobank.location.endpoint;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class SearchKeywordRequest {
    @ApiModelProperty(required = true, example = "서울역", value = "키워드 검색어")
    @NotNull(message = "검색어는 필수 값입니다.")
    private String keyword;

    @ApiModelProperty(required = true, example = "10", value = "노출 row count(최소 10, 최대 45)")
    @Min(value = 1, message = "노출 row는 1 작을 수 없습니다.") @Max(value = 15, message = "노출 row는 15보다 클 수 없습니다.")
    @NotNull(message = "노출 row count(rowPerPage)는 필수 값입니다.")
    private Integer rowsPerPage;

    @ApiModelProperty(required = true, example = "1", value = "최소 1")
    @Min(value = 1, message = "요청 page는 1보다 작을 수 없습니다.") @Max(value = 45, message = "요청 page는 45보다 클 수 없습니다.")
    @NotNull(message = "page는 필수 값입니다.")
    private Integer page;
}

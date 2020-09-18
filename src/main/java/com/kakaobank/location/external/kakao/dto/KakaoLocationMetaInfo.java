package com.kakaobank.location.external.kakao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class KakaoLocationMetaInfo {
    @JsonProperty("is_end")
    private Boolean isEnd;
    @JsonProperty("pageable_count")
    private Integer rowsPerPage;
    @JsonProperty("total_count")
    private Integer totalCount;
}

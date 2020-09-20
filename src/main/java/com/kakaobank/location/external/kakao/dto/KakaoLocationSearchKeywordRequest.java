package com.kakaobank.location.external.kakao.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter @ToString
public class KakaoLocationSearchKeywordRequest {
    private String responseFormat;
    private String query;
    private Integer page;
    private Integer size;
    private String sort;
}

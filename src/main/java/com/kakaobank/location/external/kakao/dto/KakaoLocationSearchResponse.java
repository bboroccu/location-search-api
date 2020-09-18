package com.kakaobank.location.external.kakao.dto;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class KakaoLocationSearchResponse {
    private List<KakaoLocationInfo> documents;
    private KakaoLocationMetaInfo meta;
}

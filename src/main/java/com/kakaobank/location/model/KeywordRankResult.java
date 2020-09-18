package com.kakaobank.location.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class KeywordRankResult {
    private String keyword;
    private Integer counting;
}

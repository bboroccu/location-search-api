package com.kakaobank.location.event;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class KeywordStatisticsEvent {
    private String keyword;

    public KeywordStatisticsEvent(String keyword) {
        this.keyword = keyword;
    }
}

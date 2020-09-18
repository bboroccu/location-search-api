package com.kakaobank.location.event;

import com.kakaobank.location.service.KeywordRankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KeywordStatisticsEventListener {
    private final KeywordRankService keywordRankService;

    @Autowired
    public KeywordStatisticsEventListener(KeywordRankService keywordRankService) {
        this.keywordRankService = keywordRankService;
    }

    @EventListener({KeywordStatisticsEvent.class})
    public void incrementKeywordStatistics(KeywordStatisticsEvent keywordStatisticsEvent) {
        keywordRankService.saveKeywordRanking(keywordStatisticsEvent.getKeyword());
    }
}

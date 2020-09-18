package com.kakaobank.location.service;

import com.kakaobank.location.model.KeywordRankResult;

import java.util.List;

public interface KeywordRankService {
    List<KeywordRankResult> getKeywordRanking();
    void saveKeywordRanking(String keyword);
}

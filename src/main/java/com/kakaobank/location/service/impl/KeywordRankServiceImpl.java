package com.kakaobank.location.service.impl;

import com.kakaobank.location.entity.KeywordRank;
import com.kakaobank.location.model.KeywordRankResult;
import com.kakaobank.location.repository.KeywordRankRepository;
import com.kakaobank.location.service.KeywordRankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class KeywordRankServiceImpl implements KeywordRankService {
    private final KeywordRankRepository keywordRankRepository;

    @Autowired
    public KeywordRankServiceImpl(KeywordRankRepository keywordRankRepository) {
        this.keywordRankRepository = keywordRankRepository;
    }

    @Override
    public List<KeywordRankResult> getKeywordRanking() {
        return keywordRankRepository.findTop10ByOrderByCountingDesc().stream()
                .map(this::toKeywordRankResult)
                .collect(Collectors.toList());
    }

    private KeywordRankResult toKeywordRankResult(KeywordRank keywordRank) {
        return KeywordRankResult.builder()
                .keyword(keywordRank.getKeyword())
                .counting(keywordRank.getCounting()).build();
    }

    @Transactional
    @Override
    public void saveKeywordRanking(String keyword) {
        Optional<KeywordRank> keywordRankOpt = keywordRankRepository.findByKeyword(keyword);
        KeywordRank keywordRank = keywordRankOpt
                .orElseGet(() -> {
           KeywordRank newKeywordRank = new KeywordRank();
           newKeywordRank.setKeyword(keyword);
           newKeywordRank.setCounting(0);
           newKeywordRank.setCreateAt(LocalDateTime.now());
           return newKeywordRank;
        });
        keywordRank.setCounting(keywordRank.getCounting()+1);
        keywordRank.setUpdateAt(LocalDateTime.now());
        KeywordRank savedKeywordRank = keywordRankRepository.save(keywordRank);
        log.warn("saved keywordRank : {}", savedKeywordRank);
    }
}

package com.kakaobank.location.api;

import com.kakaobank.location.model.KeywordRankResult;
import com.kakaobank.location.service.KeywordRankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.kakaobank.location.util.Constants.API_ROOT;

@RestController
@RequestMapping(API_ROOT)
public class KeywordRankingController {
    private final KeywordRankService keywordRankService;

    @Autowired
    public KeywordRankingController(KeywordRankService keywordRankService) {
        this.keywordRankService = keywordRankService;
    }

    @GetMapping("keyword/ranking")
    public ResponseEntity<List<KeywordRankResult>> getKeywordRanking() {
        return ResponseEntity.ok(keywordRankService.getKeywordRanking());
    }
}

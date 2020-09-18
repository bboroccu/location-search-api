package com.kakaobank.location.repository;

import com.kakaobank.location.entity.KeywordRank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface KeywordRankRepository extends JpaRepository<KeywordRank, Long> {
    List<KeywordRank> findTop10ByOrderByCountingDesc();
    Optional<KeywordRank> findByKeyword(String keyword);
}

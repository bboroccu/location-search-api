package com.kakaobank.location.repository;

import com.kakaobank.location.entity.KeywordRank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KeywordRepository extends JpaRepository<KeywordRank, Long> {
    List<KeywordRank> findTop10ByOrderByCountingDesc();
}

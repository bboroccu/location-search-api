package com.kakaobank.location.service.impl

import com.kakaobank.location.entity.KeywordRank
import com.kakaobank.location.repository.KeywordRankRepository
import spock.lang.Specification

class KeywordRankServiceImplTest extends Specification {
    def keywordRankRepository = Mock(KeywordRankRepository.class)

    def sut = new KeywordRankServiceImpl(keywordRankRepository)

    def "saveKeywordRanking, new keyword" ()  {
        given:
        def searchKeyword = "서울역"

        when:
        sut.saveKeywordRanking(searchKeyword)

        then:
        1 * keywordRankRepository.findByKeyword(_) >> Optional.empty()
        1 * keywordRankRepository.save(_) >> {
            arguments ->
                final KeywordRank capture = arguments[0]
                assert capture.counting == 1
        }
    }

    def "saveKeywordRanking, exist keyword" ()  {
        given:
        def searchKeyword = "서울역"
        def keywordRank = new KeywordRank(
                keyword: "서울역",
                counting: 2
        )

        when:
        sut.saveKeywordRanking(searchKeyword)

        then:
        1 * keywordRankRepository.findByKeyword(_) >> Optional.of(keywordRank)
        1 * keywordRankRepository.save(_) >> {
            arguments ->
                final KeywordRank capture = arguments[0]
                assert capture.counting == 3
        }
    }
}

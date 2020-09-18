package com.kakaobank.location.service.component

import com.fasterxml.jackson.databind.ObjectMapper
import com.kakaobank.location.endpoint.SearchKeywordRequest
import com.kakaobank.location.external.kakao.KakaoLocationSearchApiImpl
import com.kakaobank.location.external.kakao.dto.KakaoLocationSearchResponse
import spock.lang.Specification

class KakaoLocationSearcherTest extends Specification {
    def locationSearchApi = Mock(KakaoLocationSearchApiImpl.class)
    def objectMapper = new ObjectMapper()

    def sut = new KakaoLocationSearcher(locationSearchApi)

    def "ToSearchProcess, success"() {
        given:
        def searchKeywordRequest = new SearchKeywordRequest(keyword: "서울역", page: 1, rowsPerPage: 2)
        def responseBody = """{
                                  "documents": [
                                    {
                                      "address_name": "서울 용산구 동자동 43-205",
                                      "category_group_code": "",
                                      "category_group_name": "",
                                      "category_name": "교통,수송 > 기차,철도 > 기차역 > KTX정차역",
                                      "distance": "",
                                      "id": "9113903",
                                      "phone": "1544-7788",
                                      "place_name": "서울역",
                                      "place_url": "http://place.map.kakao.com/9113903",
                                      "road_address_name": "서울 용산구 한강대로 405",
                                      "x": "126.970606917394",
                                      "y": "37.5546788388674"
                                    },
                                    {
                                      "address_name": "서울 중구 남대문로5가 73-6",
                                      "category_group_code": "SW8",
                                      "category_group_name": "지하철역",
                                      "category_name": "교통,수송 > 지하철,전철 > 수도권1호선",
                                      "distance": "",
                                      "id": "27254967",
                                      "phone": "1544-7788",
                                      "place_name": "서울역 1호선",
                                      "place_url": "http://place.map.kakao.com/27254967",
                                      "road_address_name": "서울 중구 세종대로 지하 2",
                                      "x": "126.972091251236",
                                      "y": "37.5559802396321"
                                    }
                                  ],
                                  "meta": {
                                    "is_end": false,
                                    "pageable_count": 45,
                                    "same_name": {
                                      "keyword": "서울역",
                                      "region": [],
                                      "selected_region": ""
                                    },
                                    "total_count": 3
                                  }
                                }"""
        def kakaoLocationSearchResponse = objectMapper.readValue(responseBody, KakaoLocationSearchResponse.class)

        when:
        def actual = sut.toSearchProcess(searchKeywordRequest)

        then:
        1 * locationSearchApi.searchKeyword(_) >> Optional.of(kakaoLocationSearchResponse)

        actual.pageInfo.totalCount == 3
        actual.pageInfo.totalPage == 2
    }

    def "ToSearchProcess, empty"() {
        given:
        def searchKeywordRequest = new SearchKeywordRequest(keyword: "서울역", page: 1, rowsPerPage: 2)

        when:
        def actual = sut.toSearchProcess(searchKeywordRequest)

        then:
        1 * locationSearchApi.searchKeyword(_) >> Optional.empty()

        actual.pageInfo.totalCount == 0
        actual.pageInfo.totalPage == 0
    }
}

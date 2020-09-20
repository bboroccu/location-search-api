package com.kakaobank.location.service.impl


import com.google.gson.Gson
import com.kakaobank.location.endpoint.SearchKeywordRequest
import com.kakaobank.location.endpoint.SearchKeywordResponse
import com.kakaobank.location.service.component.KakaoLocationSearcher
import com.kakaobank.location.service.component.LocationSearcherManagerFactory
import org.springframework.context.ApplicationEventPublisher
import spock.lang.Specification

class LocationSearchServiceImplTest extends Specification {
    def locationSearchApiManagerFactory = Mock(LocationSearcherManagerFactory.class)
    def applicationEventPublisher = Mock(ApplicationEventPublisher.class)
    def locationSearcher = Mock(KakaoLocationSearcher.class)

    def sut = new LocationSearchServiceImpl(locationSearchApiManagerFactory, applicationEventPublisher)

    def "searchLocation, success" () {
        given:
        def searchKeywordRequest = new SearchKeywordRequest(keyword: "서울역", page: 1, rowsPerPage: 2)
        def responseBody = """{
                                  "locationInfoResultList": [
                                    {
                                      "locationId": "9113903",
                                      "placeName": "서울역",
                                      "addressName": "서울 용산구 동자동 43-205",
                                      "roadAddressName": "서울 용산구 한강대로 405",
                                      "phone": null,
                                      "locationUrl": "https://map.kakao.com/link/map/9113903"
                                    },
                                    {
                                      "locationId": "27254967",
                                      "placeName": "서울역 1호선",
                                      "addressName": "서울 중구 남대문로5가 73-6",
                                      "roadAddressName": "서울 중구 세종대로 지하 2",
                                      "phone": null,
                                      "locationUrl": "https://map.kakao.com/link/map/27254967"
                                    }
                                  ],
                                  "pageInfo": {
                                    "totalCount": 3,
                                    "totalPage": 2
                                  }
                                }"""
        def searchKeywordResponse = new Gson().fromJson(responseBody, SearchKeywordResponse.class)

        when:
        def actual = sut.searchLocation(searchKeywordRequest)

        then:
        1 * applicationEventPublisher.publishEvent(_)
        1 * locationSearchApiManagerFactory.getLocationSearcher(_) >> locationSearcher
        1 * locationSearcher.toSearchProcess(_) >> searchKeywordResponse

        assert actual.pageInfo.totalCount == searchKeywordResponse.pageInfo.totalCount
        assert actual.pageInfo.totalPage == searchKeywordResponse.pageInfo.totalPage
    }

    def "searchLocation, empty" () {
        given:
        def searchKeywordRequest = new SearchKeywordRequest(keyword: "서울역", page: 1, rowsPerPage: 2)
        def responseBody = """{
                                  "locationInfoResultList": [],
                                  "pageInfo": {
                                    "totalCount": 0,
                                    "totalPage": 0
                                  }
                                }"""
        def searchKeywordResponse = new Gson().fromJson(responseBody, SearchKeywordResponse.class)

        when:
        def actual = sut.searchLocation(searchKeywordRequest)

        then:
        1 * applicationEventPublisher.publishEvent(_)
        1 * locationSearchApiManagerFactory.getLocationSearcher(_) >> locationSearcher
        1 * locationSearcher.toSearchProcess(_) >> SearchKeywordResponse.empty()

        assert actual.pageInfo.totalCount == searchKeywordResponse.pageInfo.totalCount
        assert actual.pageInfo.totalPage == searchKeywordResponse.pageInfo.totalPage
    }
}

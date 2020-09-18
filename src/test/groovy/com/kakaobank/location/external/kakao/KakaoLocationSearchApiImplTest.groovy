package com.kakaobank.location.external.kakao

import com.kakaobank.location.config.HostProperties
import com.kakaobank.location.external.kakao.dto.KakaoLocationSearchKeywordRequest
import com.kakaobank.location.external.kakao.dto.KakaoLocationSearchResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest
import org.springframework.context.annotation.Import
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.web.util.UriComponentsBuilder
import spock.lang.Specification


import static org.springframework.test.web.client.match.MockRestRequestMatchers.method
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import static org.springframework.test.web.client.response.MockRestResponseCreators.withBadRequest
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess
import static org.springframework.test.web.client.response.MockRestResponseCreators.withUnauthorizedRequest

@Import(HostProperties.class)
@RestClientTest(value = KakaoLocationSearchApiImpl.class)
class KakaoLocationSearchApiImplTest extends Specification {
    @Autowired
    HostProperties hostProperties
    @Autowired
    KakaoLocationSearchApiImpl sut
    @Autowired
    MockRestServiceServer server

    def "SearchKeyword, success"() {
        given:
        def expectedBody = """{
                              "documents": [
                                {
                                  "address_name": "인천 남동구 간석동 757",
                                  "category_group_code": "SW8",
                                  "category_group_name": "지하철역",
                                  "category_name": "교통,수송 > 지하철,전철 > 수도권1호선",
                                  "distance": "",
                                  "id": "8674073",
                                  "phone": "032-427-7788",
                                  "place_name": "간석역 1호선",
                                  "place_url": "http://place.map.kakao.com/8674073",
                                  "road_address_name": "인천 남동구 석정로 522-14",
                                  "x": "126.693517898292",
                                  "y": "37.4647071796968"
                                },
                                {
                                  "address_name": "인천 미추홀구 주안동 35-11",
                                  "category_group_code": "CS2",
                                  "category_group_name": "편의점",
                                  "category_name": "가정,생활 > 편의점 > CU",
                                  "distance": "",
                                  "id": "2027177386",
                                  "phone": "032-424-9882",
                                  "place_name": "CU 간석북부역점",
                                  "place_url": "http://place.map.kakao.com/2027177386",
                                  "road_address_name": "인천 미추홀구 석정로 510",
                                  "x": "126.692131156434",
                                  "y": "37.4654829469173"
                                }
                              ],
                              "meta": {
                                "is_end": false,
                                "pageable_count": 34,
                                "same_name": {
                                  "keyword": "간석역",
                                  "region": [],
                                  "selected_region": ""
                                },
                                "total_count": 118
                              }
                            }"""

        def request = KakaoLocationSearchKeywordRequest.builder()
                .responseFormat("json")
                .query("간석역")
                .page(1)
                .size(30)
                .sort("accuracy")
                .build()

        def uri = UriComponentsBuilder.fromHttpUrl(hostProperties.getKakaoLocationApi() + "/v2/local/search/keyword.json")
                .queryParam("query", request.getQuery())
                .queryParam("page", request.getPage())
                .queryParam("size", request.getSize())
                .queryParam("sort", request.getSort())
                .build().toUri();

        server.expect(requestTo(uri))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(expectedBody, MediaType.APPLICATION_JSON))

        when:
        Optional<KakaoLocationSearchResponse> result = sut.searchKeyword(request)

        then:
        result.isPresent() == true
        result.get().getDocuments().size() == 2
    }

    def "SearchKeyword, serverError"() {
        given:
        def request = KakaoLocationSearchKeywordRequest.builder()
                .responseFormat("json")
                .query("간석역")
                .page(1)
                .size(30)
                .sort("accuracy")
                .build()

        def uri = UriComponentsBuilder.fromHttpUrl(hostProperties.getKakaoLocationApi() + "/v2/local/search/keyword.json")
                .queryParam("query", request.getQuery())
                .queryParam("page", request.getPage())
                .queryParam("size", request.getSize())
                .queryParam("sort", request.getSort())
                .build().toUri();

        server.expect(requestTo(uri))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withServerError())

        when:
        Optional<KakaoLocationSearchResponse> result = sut.searchKeyword(request)

        then:
        result.isPresent() == false
    }

    def "SearchKeyword, clientError"() {
        given:
        def request = KakaoLocationSearchKeywordRequest.builder()
                .responseFormat("json")
                .query("간석역")
                .page(1)
                .size(30)
                .sort("accuracy")
                .build()

        def uri = UriComponentsBuilder.fromHttpUrl(hostProperties.getKakaoLocationApi() + "/v2/local/search/keyword.json")
                .queryParam("query", request.getQuery())
                .queryParam("page", request.getPage())
                .queryParam("size", request.getSize())
                .queryParam("sort", request.getSort())
                .build().toUri();

        server.expect(requestTo(uri))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withBadRequest())

        when:
        Optional<KakaoLocationSearchResponse> result = sut.searchKeyword(request)

        then:
        result.isPresent() == false
    }

    def "SearchKeyword, withUnauthorizedRequest"() {
        given:
        def request = KakaoLocationSearchKeywordRequest.builder()
                .responseFormat("json")
                .query("간석역")
                .page(1)
                .size(30)
                .sort("accuracy")
                .build()

        def uri = UriComponentsBuilder.fromHttpUrl(hostProperties.getKakaoLocationApi() + "/v2/local/search/keyword.json")
                .queryParam("query", request.getQuery())
                .queryParam("page", request.getPage())
                .queryParam("size", request.getSize())
                .queryParam("sort", request.getSort())
                .build().toUri();

        server.expect(requestTo(uri))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withUnauthorizedRequest())

        when:
        Optional<KakaoLocationSearchResponse> result = sut.searchKeyword(request)

        then:
        result.isPresent() == false
    }
}

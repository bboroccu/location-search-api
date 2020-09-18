package com.kakaobank.location.external.kakao;

import com.kakaobank.location.config.HostProperties;
import com.kakaobank.location.external.LocationSearchApi;
import com.kakaobank.location.external.kakao.dto.KakaoLocationSearchResponse;
import com.kakaobank.location.external.kakao.dto.KakaoLocationSearchKeywordRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Slf4j
@Component("kakaoLocationSearchApi")
public class KakaoLocationSearchApiImpl implements LocationSearchApi<KakaoLocationSearchResponse> {
    @Value("${api.url-path.kakao-location-api.searchKeyword}")
    private String searchKeywordUrl;
    @Value("${api.url-path.kakao-location-api.key}")
    private String kakaoAuthKey;

    private HostProperties host;
    private RestTemplate restTemplate;

    @Autowired
    public KakaoLocationSearchApiImpl(HostProperties host, RestTemplateBuilder restTemplateBuilder) {
        this.host = host;
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public Optional<KakaoLocationSearchResponse> searchKeyword(KakaoLocationSearchKeywordRequest kakaoLocationSearchKeywordRequest) {
        URI uri = UriComponentsBuilder.fromHttpUrl(String.format(searchKeywordUrl, host.getKakaoLocationApi(), kakaoLocationSearchKeywordRequest.getResponseFormat()))
                .queryParam("query", kakaoLocationSearchKeywordRequest.getQuery())
                .queryParam("page", kakaoLocationSearchKeywordRequest.getPage())
                .queryParam("size", kakaoLocationSearchKeywordRequest.getSize())
                .queryParam("sort", kakaoLocationSearchKeywordRequest.getSort())
                .build().toUri();
        final HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", kakaoAuthKey);
        final HttpEntity<String> entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<KakaoLocationSearchResponse> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, entity, KakaoLocationSearchResponse.class);
            return Optional.ofNullable(responseEntity.getBody());
        } catch (RestClientException ex) {
            log.error("search request : {}, error : {}", kakaoLocationSearchKeywordRequest, ex.getMessage(), ex);
            return Optional.empty();
        }
    }
}

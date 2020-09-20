package com.kakaobank.location.service.component;

import com.kakaobank.location.endpoint.SearchKeywordRequest;
import com.kakaobank.location.endpoint.SearchKeywordResponse;
import com.kakaobank.location.external.LocationSearchApi;
import com.kakaobank.location.external.kakao.dto.KakaoLocationInfo;
import com.kakaobank.location.external.kakao.dto.KakaoLocationSearchKeywordRequest;
import com.kakaobank.location.external.kakao.dto.KakaoLocationSearchResponse;
import com.kakaobank.location.model.LocationInfoResult;
import com.kakaobank.location.model.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component("kakaoLocationSearchApi")
public class KakaoLocationSearcher implements LocationSearcher {
    private static final String KAKAO_MAP_URL = "https://map.kakao.com/link/map/%s";

    private LocationSearchApi<KakaoLocationSearchResponse> locationSearchApi;

    @Autowired
    public KakaoLocationSearcher(LocationSearchApi<KakaoLocationSearchResponse> locationSearchApi) {
        this.locationSearchApi = locationSearchApi;
    }

    @Override
    public SearchKeywordResponse toSearchProcess(SearchKeywordRequest searchKeywordRequest) {
        KakaoLocationSearchKeywordRequest kakaoLocationSearchKeywordRequest = KakaoLocationSearchKeywordRequest.builder()
                .responseFormat("json")
                .query(searchKeywordRequest.getKeyword())
                .page(searchKeywordRequest.getPage())
                .size(searchKeywordRequest.getRowsPerPage())
                .sort("accuracy")
                .build();
        Optional<KakaoLocationSearchResponse> locationSearchResponseOpt = locationSearchApi.searchKeyword(kakaoLocationSearchKeywordRequest);
            return locationSearchResponseOpt
                    .map(kakaoLocationSearchResponse -> toSearchKeywordResponse(kakaoLocationSearchResponse, searchKeywordRequest.getRowsPerPage()))
                .orElse(SearchKeywordResponse.empty());
    }

    private SearchKeywordResponse toSearchKeywordResponse(KakaoLocationSearchResponse kakaoLocationSearchResponse, Integer rowsPerPage) {
        List<LocationInfoResult> locationInfoResults = kakaoLocationSearchResponse.getDocuments().stream()
                .map(this::toLocationInfoResult)
                .collect(Collectors.toList());
        int totalCount = kakaoLocationSearchResponse.getMeta().getTotalCount();
        int totalPage = CALC_TOTAL_PAGE.applyAsInt(totalCount, rowsPerPage);
        PageInfo pageInfo = PageInfo.builder()
                .totalCount(totalCount)
                .totalPage(totalPage).build();
        return new SearchKeywordResponse(locationInfoResults, pageInfo);
    }

    private LocationInfoResult toLocationInfoResult(KakaoLocationInfo kakaoLocationInfo) {
        return LocationInfoResult.builder()
                .locationId(kakaoLocationInfo.getId())
                .placeName(kakaoLocationInfo.getPlaceName())
                .phone(kakaoLocationInfo.getPhone())
                .addressName(kakaoLocationInfo.getAddressName())
                .roadAddressName(kakaoLocationInfo.getRoadAddressName())
                .locationUrl(String.format(KAKAO_MAP_URL, kakaoLocationInfo.getId())).build();
    }
}

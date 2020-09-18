package com.kakaobank.location.service.impl;

import com.kakaobank.location.endpoint.SearchKeywordRequest;
import com.kakaobank.location.endpoint.SearchKeywordResponse;
import com.kakaobank.location.external.LocationSearchApi;
import com.kakaobank.location.external.LocationSearchApiManagerFactory;
import com.kakaobank.location.external.kakao.dto.KakaoLocationInfo;
import com.kakaobank.location.external.kakao.dto.KakaoLocationSearchKeywordRequest;
import com.kakaobank.location.external.kakao.dto.KakaoLocationSearchResponse;
import com.kakaobank.location.model.LocationInfoResult;
import com.kakaobank.location.model.PageInfo;
import com.kakaobank.location.service.LocationSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.ToIntBiFunction;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LocationSearchServiceImpl implements LocationSearchService {
    private static final ToIntBiFunction<Integer, Integer> CALC_TOTAL_PAGE = (totalCount, rowsPerPage) -> {
        int totalPage = totalCount / rowsPerPage;
        if (totalCount % rowsPerPage > 0) {
            totalPage++;
        }
        return totalPage;
    };

    @Value("${api.url-path.kakao-map-api.url}")
    private String kakaoMapUrl;

    private final LocationSearchApiManagerFactory locationSearchApiManagerFactory;

    @Autowired
    public LocationSearchServiceImpl(LocationSearchApiManagerFactory locationSearchApiManagerFactory) {
        this.locationSearchApiManagerFactory = locationSearchApiManagerFactory;
    }

    @Override
    public SearchKeywordResponse searchLocation(SearchKeywordRequest searchKeywordRequest) {
        LocationSearchApi locationSearchApi = locationSearchApiManagerFactory.getLocationSearchApi(LocationSearchApiManagerFactory.SearchApiType.KAKAO_API.getBeanName());
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
                .map(kakaoLocationInfo -> toLocationInfoResult(kakaoLocationInfo))
                .collect(Collectors.toList());
        int totalCount = kakaoLocationSearchResponse.getMeta().getTotalCount();
        int totalPage = CALC_TOTAL_PAGE.applyAsInt(totalCount, rowsPerPage);
        PageInfo pageInfo = PageInfo.builder()
                .totalCount(totalCount)
                .totalPage(totalPage).build();
        return SearchKeywordResponse.builder()
                .locationInfoResultList(locationInfoResults)
                .pageInfo(pageInfo).build();
    }

    private LocationInfoResult toLocationInfoResult(KakaoLocationInfo kakaoLocationInfo) {
        return LocationInfoResult.builder()
                .locationId(kakaoLocationInfo.getId())
                .placeName(kakaoLocationInfo.getPlaceName())
                .addressName(kakaoLocationInfo.getAddressName())
                .roadAddressName(kakaoLocationInfo.getRoadAddressName())
                .locationUrl(String.format(kakaoMapUrl, kakaoLocationInfo.getId())).build();
    }
}

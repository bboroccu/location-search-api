package com.kakaobank.location.service.impl;

import com.kakaobank.location.endpoint.SearchKeywordRequest;
import com.kakaobank.location.endpoint.SearchKeywordResponse;
import com.kakaobank.location.service.LocationSearchService;
import com.kakaobank.location.service.component.LocationSearcher;
import com.kakaobank.location.service.component.LocationSearcherManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LocationSearchServiceImpl implements LocationSearchService {

    private final LocationSearcherManagerFactory locationSearcherManagerFactory;

    @Autowired
    public LocationSearchServiceImpl(LocationSearcherManagerFactory locationSearcherManagerFactory) {
        this.locationSearcherManagerFactory = locationSearcherManagerFactory;
    }

    @Override
    public SearchKeywordResponse searchLocation(SearchKeywordRequest searchKeywordRequest) {
        LocationSearcher locationSearcher = locationSearcherManagerFactory.getLocationSearcher(LocationSearcherManagerFactory.SearchApiType.KAKAO_API.getBeanName());

        return locationSearcher.toSearchProcess(searchKeywordRequest);
    }
}

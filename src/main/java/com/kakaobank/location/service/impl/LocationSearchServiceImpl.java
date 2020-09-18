package com.kakaobank.location.service.impl;

import com.kakaobank.location.endpoint.SearchKeywordRequest;
import com.kakaobank.location.endpoint.SearchKeywordResponse;
import com.kakaobank.location.event.KeywordStatisticsEvent;
import com.kakaobank.location.service.LocationSearchService;
import com.kakaobank.location.service.component.LocationSearcher;
import com.kakaobank.location.service.component.LocationSearcherManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LocationSearchServiceImpl implements LocationSearchService {

    private final LocationSearcherManagerFactory locationSearcherManagerFactory;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public LocationSearchServiceImpl(LocationSearcherManagerFactory locationSearcherManagerFactory, ApplicationEventPublisher applicationEventPublisher) {
        this.locationSearcherManagerFactory = locationSearcherManagerFactory;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public SearchKeywordResponse searchLocation(SearchKeywordRequest searchKeywordRequest) {
        applicationEventPublisher.publishEvent(new KeywordStatisticsEvent(searchKeywordRequest.getKeyword()));

        LocationSearcher locationSearcher = locationSearcherManagerFactory.getLocationSearcher(LocationSearcherManagerFactory.SearchApiType.KAKAO_API.getBeanName());

        return locationSearcher.toSearchProcess(searchKeywordRequest);
    }
}

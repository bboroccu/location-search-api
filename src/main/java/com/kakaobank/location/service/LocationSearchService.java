package com.kakaobank.location.service;

import com.kakaobank.location.endpoint.SearchKeywordRequest;
import com.kakaobank.location.endpoint.SearchKeywordResponse;

public interface LocationSearchService {
    SearchKeywordResponse searchLocation(SearchKeywordRequest searchKeywordRequest);
}

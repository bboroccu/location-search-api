package com.kakaobank.location.service.component;

import com.kakaobank.location.endpoint.SearchKeywordRequest;
import com.kakaobank.location.endpoint.SearchKeywordResponse;

import java.util.function.ToIntBiFunction;

public interface LocationSearcher {
    ToIntBiFunction<Integer, Integer> CALC_TOTAL_PAGE = (totalCount, rowsPerPage) -> {
        int totalPage = totalCount / rowsPerPage;
        if (totalCount % rowsPerPage > 0) {
            totalPage++;
        }
        return totalPage;
    };

    SearchKeywordResponse toSearchProcess(SearchKeywordRequest searchKeywordRequest);
}

package com.kakaobank.location.endpoint;

import com.kakaobank.location.model.LocationInfoResult;
import com.kakaobank.location.model.PageInfo;
import lombok.*;

import java.util.Collections;
import java.util.List;

@Getter
@ToString
@AllArgsConstructor
public class SearchKeywordResponse {
    private List<LocationInfoResult> locationInfoResultList;
    private PageInfo pageInfo;

    public static SearchKeywordResponse empty() {
        return new SearchKeywordResponse(Collections.emptyList(), PageInfo.builder()
                .totalCount(0)
                .totalPage(0).build());
    }
}

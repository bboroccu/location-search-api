package com.kakaobank.location.endpoint;

import com.kakaobank.location.model.LocationInfoResult;
import com.kakaobank.location.model.PageInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Collections;
import java.util.List;

@Getter
@ToString
@Builder
public class SearchKeywordResponse {
    private List<LocationInfoResult> locationInfoResultList;
    private PageInfo pageInfo;

    public static SearchKeywordResponse empty() {
        return SearchKeywordResponse.builder()
                .locationInfoResultList(Collections.emptyList())
                .pageInfo(PageInfo.builder()
                        .totalCount(0)
                        .totalPage(0).build()).build();
    }
}

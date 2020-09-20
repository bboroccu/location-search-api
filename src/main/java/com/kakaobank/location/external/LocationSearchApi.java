package com.kakaobank.location.external;

import com.kakaobank.location.external.kakao.dto.KakaoLocationSearchKeywordRequest;

import java.util.Optional;

public interface LocationSearchApi<T> {
    Optional<T> searchKeyword(KakaoLocationSearchKeywordRequest kakaoLocationSearchKeywordRequest);
}

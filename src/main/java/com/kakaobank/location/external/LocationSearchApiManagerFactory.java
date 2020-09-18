package com.kakaobank.location.external;

public interface LocationSearchApiManagerFactory {
    LocationSearchApi getLocationSearchApi(String beanName);

    enum SearchApiType {
        KAKAO_API("kakaoLocationSearchApi");
        private final String beanName;

        SearchApiType(String beanName) {
            this.beanName = beanName;
        }

        public String getBeanName() {
            return beanName;
        }
    }
}

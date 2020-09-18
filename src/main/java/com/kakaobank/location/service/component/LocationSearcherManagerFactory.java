package com.kakaobank.location.service.component;

public interface LocationSearcherManagerFactory {
    LocationSearcher getLocationSearcher(String beanName);

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

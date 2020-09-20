package com.kakaobank.location.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class LocationInfoResult {
    private String locationId;
    private String placeName;
    private String addressName;
    private String roadAddressName;
    private String phone;
    private String locationUrl;
}

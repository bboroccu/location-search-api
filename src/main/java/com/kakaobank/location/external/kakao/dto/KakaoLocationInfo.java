package com.kakaobank.location.external.kakao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class KakaoLocationInfo {
    @JsonProperty("address_name")
    private String addressName;
    @JsonProperty("category_group_code")
    private String categoryGroupCode;
    @JsonProperty("category_group_name")
    private String categoryGroupName;
    @JsonProperty("category_name")
    private String categoryName;
    @JsonProperty("distance")
    private String distance;
    @JsonProperty("id")
    private String id;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("place_name")
    private String placeName;
    @JsonProperty("place_url")
    private String placeUrl;
    @JsonProperty("road_address_name")
    private String roadAddressName;
    @JsonProperty("x")
    private String longitude;
    @JsonProperty("y")
    private String latitude;
}

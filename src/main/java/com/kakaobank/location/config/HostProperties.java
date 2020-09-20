package com.kakaobank.location.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import static com.kakaobank.location.config.HostProperties.PREFIX;

@Getter
@Setter
@Component(value = "host")
@ConfigurationProperties(prefix = PREFIX)
public class HostProperties {
    public static final String PREFIX = "spring.host";
    private String kakaoLocationApi;
}

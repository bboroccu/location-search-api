package com.kakaobank.location.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
public class RestTemplateConfig {
    private static final int DEFAULT_CONNECTIONS_TIMEOUT_MILLISECONDS = (5 * 1000);
    private static final int DEFAULT_READ_TIMEOUT_MILLISECONDS = (30 * 1000);

    @Bean
    public HttpClient httpClient() {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(DEFAULT_CONNECTIONS_TIMEOUT_MILLISECONDS)
                .setSocketTimeout(DEFAULT_READ_TIMEOUT_MILLISECONDS)
                .setConnectionRequestTimeout(10 * 1000)
                .build();

        return HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .setMaxConnTotal(200)
                .setMaxConnPerRoute(10)
                .setConnectionTimeToLive(55, TimeUnit.SECONDS)
                .evictIdleConnections(55, TimeUnit.SECONDS)
                .build();
    }

    @Bean
    public CustomRestTemplateCustomizer restTemplateCustomizer(HttpClient httpClient) {
        return new CustomRestTemplateCustomizer(httpClient);
    }

    @Bean
    @DependsOn({"restTemplateCustomizer"})
    public RestTemplateBuilder restTemplateBuilder(CustomRestTemplateCustomizer customRestTemplateCustomizer) {
        return new RestTemplateBuilder(customRestTemplateCustomizer);
    }
}
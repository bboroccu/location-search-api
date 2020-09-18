package com.kakaobank.location.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import com.google.common.collect.Lists;
import org.apache.http.client.HttpClient;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_HTML;

public class CustomRestTemplateCustomizer implements RestTemplateCustomizer {
    private HttpClient httpClient;

    public CustomRestTemplateCustomizer(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public void customize(RestTemplate restTemplate) {
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new AfterburnerModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        List<MediaType> supportedMediaTypes = Lists.newArrayList(APPLICATION_JSON, TEXT_HTML);

        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter(objectMapper);
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);
        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(UTF_8);
        stringHttpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);

        restTemplate.setMessageConverters(Lists.newArrayList(stringHttpMessageConverter, mappingJackson2HttpMessageConverter));
    }
}

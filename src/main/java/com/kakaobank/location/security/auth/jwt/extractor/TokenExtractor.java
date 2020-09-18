package com.kakaobank.location.security.auth.jwt.extractor;

public interface TokenExtractor {
    public String extract(String payload);
}

package com.kakaobank.location.security.auth.jwt.extractor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;

@Component
public class JwtHeaderTokenExtractor implements TokenExtractor {

    @Override
    public String extract(String payload) {
        if (StringUtils.isBlank(payload))
            throw new AuthenticationServiceException("Authorization header cannot be blank.");
        return payload;
    }
}


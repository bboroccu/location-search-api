package com.kakaobank.location.security.auth.jwt.verifier;

public interface TokenVerifier {
    boolean verify(String jti);
}

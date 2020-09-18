package com.kakaobank.location.endpoint;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ErrorCode {
    SUCCESS(0),
    NOT_FOUND_ID(1),
    FAIL_PASSWORD(2),
    NOT_FOUND_ROLE(3),
    FAIL_REFRESH_PASSWORD(4),
    FAIL(5),
    AUTHENTICATION(10),
    TOKEN_EXPIRED(11),
    NOTFOUND_REFRESH_TOKEN(12),
    UNKNOWN_ERROR(13),
    INVALID_PARAMETER(14);
    
    private int errorCode;

    private ErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @JsonValue
    public int getErrorCode() {
        return errorCode;
    }
}

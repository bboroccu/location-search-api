package com.kakaobank.location.endpoint;

import com.kakaobank.location.model.ErrorCode;
import lombok.Getter;

@Getter
public class ErrorResponse {
    private final String message;
    private final ErrorCode errorCode;

    protected ErrorResponse(final String message, final ErrorCode errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }

    public static ErrorResponse of(final String message, final ErrorCode errorCode) {
        return new ErrorResponse(message, errorCode);
    }
}

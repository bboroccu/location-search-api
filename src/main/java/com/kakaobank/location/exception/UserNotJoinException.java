package com.kakaobank.location.exception;

import com.kakaobank.location.model.ErrorCode;

public class UserNotJoinException extends RuntimeException {
    private ErrorCode errorCode;

    public UserNotJoinException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}

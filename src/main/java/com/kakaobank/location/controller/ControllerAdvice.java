package com.kakaobank.location.controller;

import com.kakaobank.location.endpoint.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.kakaobank.location.model.ErrorCode.INVALID_PARAMETER;

@Slf4j
@RestControllerAdvice(basePackages = "com.kakaobank.location.controller")
public class ControllerAdvice {
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBindException(BindException bex) {
        return ErrorResponse.of(bex.getFieldError().getDefaultMessage(), INVALID_PARAMETER);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMissingRequestHeaderException(MissingRequestHeaderException mex) {
        return ErrorResponse.of(mex.getMessage(), INVALID_PARAMETER);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIllegalArgumentException(IllegalArgumentException iex) {
        return ErrorResponse.of(iex.getMessage(), INVALID_PARAMETER);
    }
}

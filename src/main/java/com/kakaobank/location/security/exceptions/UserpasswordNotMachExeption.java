package com.kakaobank.location.security.exceptions;

import org.springframework.security.authentication.AuthenticationServiceException;

public class UserpasswordNotMachExeption extends AuthenticationServiceException {
    public UserpasswordNotMachExeption(String msg) {
        super(msg);
    }
}

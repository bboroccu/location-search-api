package com.kakaobank.location.service;

import com.kakaobank.location.endpoint.JoinUserRequest;
import com.kakaobank.location.entity.Users;

import java.util.Optional;

public interface UserService {
    Optional<Users> getUserInfo(String userId);
    String joinUser(JoinUserRequest joinUserRequest);
}

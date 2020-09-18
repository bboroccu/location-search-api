package com.kakaobank.location.service.impl;

import com.kakaobank.location.entity.Users;
import com.kakaobank.location.repository.UsersRepository;
import com.kakaobank.location.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UsersRepository usersRepository;

    @Autowired
    public UserServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public Optional<Users> getUserInfo(String userId) {
        return this.usersRepository.findByUserId(userId);
    }
}

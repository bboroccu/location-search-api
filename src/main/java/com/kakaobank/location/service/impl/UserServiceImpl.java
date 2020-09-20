package com.kakaobank.location.service.impl;

import com.kakaobank.location.endpoint.JoinUserRequest;
import com.kakaobank.location.entity.UserRole;
import com.kakaobank.location.entity.Users;
import com.kakaobank.location.exception.UserNotJoinException;
import com.kakaobank.location.model.ErrorCode;
import com.kakaobank.location.repository.UsersRepository;
import com.kakaobank.location.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    @Override
    public String joinUser(JoinUserRequest joinUserRequest) {
        Optional<Users> usersOpt = getUserInfo(joinUserRequest.getUserId());
        if (usersOpt.isPresent()) {
            throw new UserNotJoinException("이미 존재하는 사용자입니다.", ErrorCode.ALREADY_USER_EXIST);
        }
        try {
            Users user = new Users();
            user.setUserId(joinUserRequest.getUserId());
            user.setPassword(DigestUtils.md5Hex(joinUserRequest.getPassword()));
            user.setUserRole(UserRole.USER);
            LocalDateTime now = LocalDateTime.now();
            user.setCreateAt(now);
            user.setUpdateAt(now);
            Users savedUser = usersRepository.save(user);
            return savedUser.getUserId();
        } catch (Exception ex) {
            log.error("joinUser request : {}, message : {}", joinUserRequest, ex.getMessage(), ex);
            throw new UserNotJoinException("사용자 등록에 실패 했습니다.", ErrorCode.UNKNOWN_ERROR);
        }
    }
}

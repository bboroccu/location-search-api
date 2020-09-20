package com.kakaobank.location.service.impl

import com.kakaobank.location.endpoint.JoinUserRequest
import com.kakaobank.location.entity.Users
import com.kakaobank.location.exception.UserNotJoinException
import com.kakaobank.location.repository.UsersRepository
import spock.lang.Specification

import java.sql.SQLException

class UserServiceImplTest extends Specification {
    def usersRepository = Mock(UsersRepository.class)

    def sut = new UserServiceImpl(usersRepository)

    def "JoinUser, save success" () {
        given:
        def joinUserRequest = new JoinUserRequest(userId: "test", password: "1234")
        def savedUser = new Users(userId: joinUserRequest.userId)

        when:
        def actual = sut.joinUser(joinUserRequest)

        then:
        1 * usersRepository.findByUserId(_) >> Optional.empty()
        1 * usersRepository.save(_) >> savedUser

        assert actual == joinUserRequest.userId
    }

    def "JoinUser, user exist" () {
        given:
        def joinUserRequest = new JoinUserRequest(userId: "test", password: "1234")
        def savedUser = new Users(userId: joinUserRequest.userId)

        when:
        def actual = sut.joinUser(joinUserRequest)

        then:
        1 * usersRepository.findByUserId(_) >> Optional.of(savedUser)
        0 * usersRepository.save(_)

        def e = thrown(UserNotJoinException.class)
        assert e.message == "이미 존재하는 사용자입니다."
    }

    def "JoinUser, save sqlException" () {
        given:
        def joinUserRequest = new JoinUserRequest(userId: "test", password: "1234")

        when:
        def actual = sut.joinUser(joinUserRequest)

        then:
        1 * usersRepository.findByUserId(_) >> Optional.empty()
        1 * usersRepository.save(_) >> { throw new SQLException() }

        def e = thrown(UserNotJoinException.class)
        assert e.message == "사용자 등록에 실패 했습니다."
    }
}

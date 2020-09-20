package com.kakaobank.location.security.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaobank.location.model.ErrorCode;
import com.kakaobank.location.endpoint.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExceptionResponse {
    private ExceptionResponse() {
        throw new IllegalStateException("Utility class");
    }
    public static void commonExceptionResponse(ObjectMapper mapper, HttpServletResponse httpServletResponse, Exception e) throws IOException {
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setCharacterEncoding("UTF-8");
        if (e instanceof BadCredentialsException)
            mapper.writeValue(httpServletResponse.getWriter(), ErrorResponse.of("정상적인 토큰이 아닙니다.", ErrorCode.NOTFOUND_REFRESH_TOKEN));
        else if (e instanceof UserpasswordNotMachExeption)
            mapper.writeValue(httpServletResponse.getWriter(), ErrorResponse.of("등록된 비밀번호와 다릅니다.", ErrorCode.FAIL_PASSWORD));
        else if (e instanceof JwtExpiredTokenException)
            mapper.writeValue(httpServletResponse.getWriter(), ErrorResponse.of("인증토큰이 만료되었습니다.", ErrorCode.TOKEN_EXPIRED));
        else if (e instanceof AuthMethodNotSupportedException)
            mapper.writeValue(httpServletResponse.getWriter(), ErrorResponse.of(e.getMessage(), ErrorCode.AUTHENTICATION));
        else if (e instanceof UsernameNotFoundException)
            mapper.writeValue(httpServletResponse.getWriter(), ErrorResponse.of("아이디가 존재하지 않습니다.", ErrorCode.NOT_FOUND_ID));
        else if (e instanceof InsufficientAuthenticationException)
            mapper.writeValue(httpServletResponse.getWriter(), ErrorResponse.of("권한이 없습니다.", ErrorCode.NOT_FOUND_ROLE));
        mapper.writeValue(httpServletResponse.getWriter(), ErrorResponse.of("Authentication failed", ErrorCode.AUTHENTICATION));
    }
}

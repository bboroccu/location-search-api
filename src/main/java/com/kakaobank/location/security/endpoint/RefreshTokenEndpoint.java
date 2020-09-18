package com.kakaobank.location.security.endpoint;

import com.kakaobank.location.entity.Users;
import com.kakaobank.location.security.auth.jwt.extractor.TokenExtractor;
import com.kakaobank.location.security.auth.jwt.verifier.TokenVerifier;
import com.kakaobank.location.security.config.JwtSettings;
import com.kakaobank.location.security.exceptions.InvalidJwtToken;
import com.kakaobank.location.security.model.UserContext;
import com.kakaobank.location.security.model.token.JwtToken;
import com.kakaobank.location.security.model.token.JwtTokenFactory;
import com.kakaobank.location.security.model.token.RawAccessJwtToken;
import com.kakaobank.location.security.model.token.RefreshToken;
import com.kakaobank.location.service.UserService;
import com.kakaobank.location.util.Constants;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
public class RefreshTokenEndpoint {
    @Autowired
    private JwtTokenFactory tokenFactory;
    @Autowired
    private JwtSettings jwtSettings;
    @Autowired
    private UserService userService;
    @Autowired
    private TokenVerifier tokenVerifier;
    @Autowired
    @Qualifier("jwtHeaderTokenExtractor")
    private TokenExtractor tokenExtractor;

    @RequestMapping(value = "/auth/token", method = RequestMethod.GET)
    public
    @ResponseBody
    JwtToken refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String tokenPayload = tokenExtractor.extract(request.getHeader(Constants.AUTHENTICATION_HEADER_NAME));
        RawAccessJwtToken rawToken = new RawAccessJwtToken(tokenPayload);
        RefreshToken refreshToken = RefreshToken.create(rawToken, jwtSettings.getTokenSigningKey()).orElseThrow(() -> new InvalidJwtToken());

        String jti = refreshToken.getJti();
        if (!tokenVerifier.verify(jti))
            throw new InvalidJwtToken();
        String subject = refreshToken.getSubject();
        Users user = userService.getUserInfo(subject).orElseThrow(() -> new UsernameNotFoundException("User not found : " + subject));
        if (user.getUserRole() == null) throw new InsufficientAuthenticationException("user has no roles assinged");
        List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(user.getUserRole().authority()));
        UserContext userContext = UserContext.create(user.getUserId().toString(), authorities);
        return tokenFactory.createAccessJwtToken(userContext);
    }

    @RequestMapping(value = "auth/check", method = RequestMethod.GET)
    public
    @ResponseBody boolean checkToken(HttpServletRequest request) {
        String token = request.getHeader(Constants.AUTHENTICATION_HEADER_NAME);
        try {
            Jws<Claims> jwsClaims = Jwts.parser().setSigningKey(jwtSettings.getTokenSigningKey()).parseClaimsJws(token);
            if (jwsClaims == null)
                return false;
            else
                return true;
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException ex) {
            log.error("Invalid JWT Token", ex);
            return false;
        } catch (ExpiredJwtException expiredEx) {
            log.info("JWT Token is expired", expiredEx);
            return false;
        }
    }
}

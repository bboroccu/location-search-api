package com.kakaobank.location.security.auth.ajax;

import com.kakaobank.location.entity.Users;
import com.kakaobank.location.security.exceptions.UserpasswordNotMachExeption;
import com.kakaobank.location.security.model.UserContext;
import com.kakaobank.location.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;

@Component
public class AjaxAuthenticationProvider implements AuthenticationProvider {
    private final BCryptPasswordEncoder encoder;
    private final UserService userService;

    @Autowired
    public AjaxAuthenticationProvider(final UserService userService, final BCryptPasswordEncoder encoder) {
        this.userService = userService;
        this.encoder = encoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.notNull(authentication, "No authentication data provided");
        String userId = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        Users user = userService.getUserInfo(userId).orElseThrow(() -> new UsernameNotFoundException("User not found : " + userId));
        if (user.getUserRole() == null) throw new InsufficientAuthenticationException("User has no Roles Assigned.");
        if (encoder.matches(password, user.getPasswd()))
            throw new UserpasswordNotMachExeption("Authentication Failed, Username or Password no valid.");
        List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(user.getUserRole().authority()));
        UserContext userContext = UserContext.create(user.getUserId(), authorities);
        return new UsernamePasswordAuthenticationToken(userContext, null, userContext.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authenticate) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authenticate));
    }
}

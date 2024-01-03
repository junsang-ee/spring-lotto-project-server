package com.lotto.web.service;

import com.lotto.web.config.jwt.JwtTokenProvider;
import com.lotto.web.constants.UserRole;
import com.lotto.web.model.dto.request.LoginRequest;
import com.lotto.web.model.dto.request.SignupRequest;
import com.lotto.web.model.entity.UserEntity;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public UserEntity signup(UserRole role, SignupRequest request) {
        return userService.save(role, request);
    }

    @Override
    public String login(String userAgent, LoginRequest login) {
        UserEntity user = userService.getUserByEmail(login.getEmail());
        userService.checkAccount(user, login.getPassword());
        return jwtTokenProvider.create(userAgent, user.getId(), user.getRole());
    }
}

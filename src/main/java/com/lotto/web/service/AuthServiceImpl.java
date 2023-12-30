package com.lotto.web.service;

import com.lotto.web.constants.UserRole;
import com.lotto.web.model.dto.request.SignupRequest;
import com.lotto.web.model.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;

    @Override
    public UserEntity me() {
        return null;
    }

    @Override
    public UserEntity signup(UserRole role, SignupRequest request) {
        return userService.save(role, request);
    }
}

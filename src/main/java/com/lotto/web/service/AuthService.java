package com.lotto.web.service;

import com.lotto.web.model.dto.request.LoginRequest;
import com.lotto.web.model.entity.UserEntity;

public interface AuthService {
    UserEntity me();

    UserEntity signup();
}

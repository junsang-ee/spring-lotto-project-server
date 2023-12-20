package com.lotto.web.service;

import com.lotto.web.model.entity.UserEntity;

public interface UserService {
    UserEntity get(String userId);
}

package com.lotto.web.service;

import com.lotto.web.model.entity.UserEntity;

import java.util.Optional;

public interface UserService {

    Optional<UserEntity> get(String userId);
}

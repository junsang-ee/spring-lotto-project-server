package com.lotto.web.service;

import com.lotto.web.constants.UserRole;
import com.lotto.web.model.dto.request.SignupRequest;
import com.lotto.web.model.entity.UserEntity;

import java.util.Optional;

public interface UserService {

    Optional<UserEntity> get(String userId);

    UserEntity getUser(String userId);

    Optional<UserEntity> getByEmail(String email);

    UserEntity save(UserRole role, SignupRequest request);


}

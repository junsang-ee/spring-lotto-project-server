package com.lotto.web.service;

import com.lotto.web.constants.UserRole;
import com.lotto.web.model.dto.request.LoginRequest;
import com.lotto.web.model.dto.request.SignupRequest;
import com.lotto.web.model.dto.response.UserDetailResponse;
import com.lotto.web.model.entity.UserEntity;

import java.util.Optional;

public interface UserService {


    Optional<UserEntity> get(String userId);

    UserEntity getUser(String userId);

    UserDetailResponse getDetail(String userId);

    Optional<UserEntity> getByEmail(String email);

    UserEntity getUserByEmail(String email);

    UserEntity save(UserRole role, SignupRequest request);

    boolean getIsDuplicatedEmail(String email);

    void checkAccount(UserEntity user, String password);

    boolean updatePassword(String userId, String oldPassword, String newPassword);
}

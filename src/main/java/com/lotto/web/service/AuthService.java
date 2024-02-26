package com.lotto.web.service;

import com.lotto.web.constants.UserRole;
import com.lotto.web.model.dto.request.LoginRequest;
import com.lotto.web.model.dto.request.SignupRequest;
import com.lotto.web.model.dto.request.VerifyAuthRequest;
import com.lotto.web.model.dto.request.VerifyEmailRequest;
import com.lotto.web.model.entity.UserEntity;

public interface AuthService {

    UserEntity signup(UserRole role, SignupRequest request);

    String login(String userAgent, LoginRequest login);

    String sendAuthCode(VerifyEmailRequest request);

    boolean verifyEmail(VerifyAuthRequest request);

    String resetPassword(VerifyEmailRequest request);

}

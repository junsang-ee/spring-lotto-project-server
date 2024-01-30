package com.lotto.web.service;

import com.lotto.web.constants.UserRole;
import com.lotto.web.model.dto.request.LoginRequest;
import com.lotto.web.model.dto.request.SignupRequest;
import com.lotto.web.model.dto.request.VerifyAuthRequest;
import com.lotto.web.model.dto.request.VerifyEmailRequest;

public interface AuthService {

    boolean signup(UserRole role, SignupRequest request);

    String login(String userAgent, LoginRequest login);

    boolean sendVerifyCode(VerifyEmailRequest request);

    boolean verifyEmail(VerifyAuthRequest request);

    boolean resetPassword(VerifyEmailRequest request);

}

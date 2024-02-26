package com.lotto.web.service;

import com.lotto.web.config.jwt.JwtTokenProvider;
import com.lotto.web.constants.UserRole;
import com.lotto.web.constants.cache.CacheType;
import com.lotto.web.constants.messages.ErrorMessage;
import com.lotto.web.exception.custom.AuthException;
import com.lotto.web.exception.custom.DuplicatedException;

import com.lotto.web.model.dto.mail.MailTemplate;
import com.lotto.web.model.dto.request.LoginRequest;
import com.lotto.web.model.dto.request.SignupRequest;
import com.lotto.web.model.dto.request.VerifyAuthRequest;
import com.lotto.web.model.dto.request.VerifyEmailRequest;
import com.lotto.web.model.entity.UserEntity;

import com.lotto.web.service.caffeine.CaffeineService;
import com.lotto.web.service.mail.MailService;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Random;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;

    private final JwtTokenProvider jwtTokenProvider;

    private final MailService mailService;

    private final CaffeineService caffeineService;


    @Override
    @Transactional
    public UserEntity signup(UserRole role, SignupRequest request) {
        return userService.save(role, request);
    }

    @Override
    public String login(String userAgent, LoginRequest login) {
        UserEntity user = userService.getUserByEmail(login.getEmail());
        userService.checkAccount(user, login.getPassword());
        return jwtTokenProvider.create(userAgent, user.getId(), user.getRole());
    }

    @Override
    public String sendAuthCode(VerifyEmailRequest request) {
        String email = request.getEmail();
        if (userService.getIsDuplicatedEmail(email))
            throw new DuplicatedException(ErrorMessage.AUTH_DUPLICATED_EMAIL);
        String authCode = getAuthCode();
        mailService.sendMail(
                email,
                MailTemplate.VERIFY_EMAIL,
                authCode
        );
        return authCode;
    }

    @Override
    public boolean verifyEmail(VerifyAuthRequest request) {
        String cachedAuthCode;
        try {
            cachedAuthCode = caffeineService.get(
                    CacheType.SIGNUP_CODE,
                    request.getEmail()
            );
        } catch (NullPointerException e) {
            throw new AuthException(ErrorMessage.AUTH_CODE_EXPIRED);
        }
        if (!cachedAuthCode.equals(request.getAuthCode()))
            throw new AuthException(ErrorMessage.AUTH_CODE_INVALID);
        return true;
    }

    @Override
    @Transactional
    public String resetPassword(VerifyEmailRequest request) {
        String email = request.getEmail();
        String temp = String.valueOf(getTempPassword());
        UserEntity user = userService.getUserByEmail(email);
        userService.updatePassword(user.getId(), null, temp);
        return temp;
    }

    private String getAuthCode() {
        Random random = new Random();
        int randomNumber = random.nextInt(88888) + 11111;
        return String.valueOf(randomNumber);
    }

    private int getTempPassword() {
        Random random = new Random();
        return random.nextInt(888888) + 111111;
    }
}

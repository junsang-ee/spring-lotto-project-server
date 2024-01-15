package com.lotto.web.service;

import com.lotto.web.config.jwt.JwtTokenProvider;
import com.lotto.web.constants.UserRole;
import com.lotto.web.constants.messages.ErrorMessage;
import com.lotto.web.exception.custom.DuplicatedException;
import com.lotto.web.model.dto.mail.MailTemplate;
import com.lotto.web.model.dto.request.LoginRequest;
import com.lotto.web.model.dto.request.SignupRequest;
import com.lotto.web.model.dto.request.VerifyRequest;
import com.lotto.web.model.entity.UserEntity;

import com.lotto.web.service.mail.MailService;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;

    private final JwtTokenProvider jwtTokenProvider;

    private final MailService mailService;

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

    @Override
    public boolean sendVerifyCode(VerifyRequest request) {
        String email = request.getEmail();
        if (userService.getIsDuplicatedEmail(email))
            throw new DuplicatedException(ErrorMessage.AUTH_DUPLICATED_EMAIL);
        String authCode = getAuthCode();
        mailService.sendMail(
                email,
                MailTemplate.VERIFY_EMAIL.getSubject(),
                authCode);
        return true;
    }

    private String getAuthCode() {
        Random random = new Random();
        int randomNubmer = random.nextInt(88888) + 11111;
        return String.valueOf(randomNubmer);
    }
}

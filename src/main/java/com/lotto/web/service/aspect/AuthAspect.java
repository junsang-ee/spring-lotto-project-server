package com.lotto.web.service.aspect;

import com.lotto.web.constants.cache.CacheType;
import com.lotto.web.model.dto.mail.MailTemplate;
import com.lotto.web.model.dto.request.VerifyAuthRequest;
import com.lotto.web.model.dto.request.VerifyEmailRequest;
import com.lotto.web.service.caffeine.CaffeineService;
import com.lotto.web.service.mail.MailService;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AuthAspect {

    private final MailService mailService;

    private final CaffeineService caffeineService;

    @AfterReturning(value = "execution(* com..AuthService.resetPassword(..)) && args(request)",
            returning = "temp", argNames = "request, temp")
    public void resetPassword(VerifyEmailRequest request, String temp) {
        mailService.sendMail(
                request.getEmail(),
                MailTemplate.RESET_PASSWORD,
                temp
        );
    }

    @AfterReturning(value = "execution(* com..AuthService.sendAuthCode(..)) && args(request)",
            returning = "authCode", argNames = "request, authCode")
    public void saveAuthCodeInCache(VerifyEmailRequest request, String authCode) {
        try {
            if (caffeineService.get(CacheType.SIGNUP_CODE, request.getEmail()) != null) {
                caffeineService.remove(
                        CacheType.SIGNUP_CODE,
                        request.getEmail()
                );
            }
        } catch (NullPointerException ignored) {
        } finally {
            caffeineService.put(
                    CacheType.SIGNUP_CODE,
                    request.getEmail(),
                    authCode
            );
        }
    }

    @AfterReturning(value = "execution(* com..AuthService.verifyEmail(..)) && args(request)",
            returning = "isVerify", argNames = "request, isVerify")
    public void verifyEmail(VerifyAuthRequest request, boolean isVerify) {
        if (isVerify)
            caffeineService.remove(
                    CacheType.SIGNUP_CODE,
                    request.getEmail()
            );
    }


}

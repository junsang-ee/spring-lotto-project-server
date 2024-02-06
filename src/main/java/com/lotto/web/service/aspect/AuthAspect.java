package com.lotto.web.service.aspect;

import com.lotto.web.model.dto.mail.MailTemplate;
import com.lotto.web.model.dto.request.VerifyEmailRequest;
import com.lotto.web.service.mail.MailService;
import lombok.RequiredArgsConstructor;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class AuthAspect {

    private final MailService mailService;

    @AfterReturning(value = "execution(* com..AuthService.resetPassword(..)) && args(request)",
            returning = "temp", argNames = "request, temp")
    public void resetPassword(VerifyEmailRequest request, String temp) {
        mailService.sendMail(
                request.getEmail(),
                MailTemplate.RESET_PASSWORD,
                temp
        );
    }
}

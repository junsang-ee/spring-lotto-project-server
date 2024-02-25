package com.lotto.web.service.aspect;

import com.lotto.web.constants.UserRole;
import com.lotto.web.model.dto.request.LoginRequest;
import com.lotto.web.model.dto.request.SignupRequest;
import com.lotto.web.model.dto.request.VerifyEmailRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class TestAspect {


    @Around(value = "execution(* com..AuthService.signup(..)) && args(role, request)",
            argNames = "joinPoint, role, request")
    public String signupTest(ProceedingJoinPoint joinPoint, UserRole role, SignupRequest request) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object result = joinPoint.proceed();

        stopWatch.stop();
        log.info("signupTest : " + stopWatch.prettyPrint());
        return (String) result;
    }

    @Around(value = "execution(* com..AuthService.login(..)) && args(userAgent, login)",
            argNames = "joinPoint, userAgent, login")
    public String loginTest(ProceedingJoinPoint joinPoint, String userAgent, LoginRequest login) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object result = joinPoint.proceed();

        stopWatch.stop();
        log.info("loginTest :: " + stopWatch.prettyPrint());
        return (String) result;
    }

}

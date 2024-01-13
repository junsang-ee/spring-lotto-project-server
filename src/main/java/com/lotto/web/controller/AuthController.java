package com.lotto.web.controller;

import com.lotto.web.constants.UserRole;
import com.lotto.web.model.dto.request.LoginRequest;
import com.lotto.web.model.dto.request.SignupRequest;
import com.lotto.web.model.dto.request.VerifyRequest;
import com.lotto.web.model.dto.response.common.ApiSuccessResponse;
import com.lotto.web.model.dto.response.common.TokenResponse;
import com.lotto.web.model.entity.UserEntity;
import com.lotto.web.service.AuthService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthController extends BaseController {

    private final AuthService authService;


    /* 로그인 */
    @PostMapping("/login")
    public ApiSuccessResponse<TokenResponse> login(HttpServletRequest request,
                                                   @Valid @RequestBody LoginRequest loginRequest) {
        String userAgent = request.getHeader("User-Agent");
        return wrap(new TokenResponse(authService.login(userAgent, loginRequest)));
    }

    /* 일반 사용자 회원가입 */
    @PostMapping("/signup")
    public ApiSuccessResponse<UserEntity> signup(@RequestBody SignupRequest request) {
        return wrap(authService.signup(UserRole.USER, request));
    }

    /* 관리자 회원가입 */
    @PostMapping("/signup/admin")
    public ApiSuccessResponse<UserEntity> signupAdmin(@RequestBody SignupRequest request) {
        return wrap(authService.signup(UserRole.ADMIN, request));
    }

    @PostMapping("/email/code")
    public ApiSuccessResponse<Boolean> sendCode(@RequestBody @Valid VerifyRequest request) {
        return wrap(authService.sendVerifyCode(request));
    }

    @PatchMapping("/{userId}")
    public ApiSuccessResponse<String> updateAuthority() {
        return null;
    }
}

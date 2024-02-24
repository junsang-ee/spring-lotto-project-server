package com.lotto.web.controller;

import com.lotto.web.constants.UserRole;
import com.lotto.web.model.dto.request.LoginRequest;
import com.lotto.web.model.dto.request.SignupRequest;
import com.lotto.web.model.dto.request.VerifyAuthRequest;
import com.lotto.web.model.dto.request.VerifyEmailRequest;
import com.lotto.web.model.dto.response.common.ApiSuccessResponse;
import com.lotto.web.model.dto.response.common.TokenResponse;
import com.lotto.web.service.AuthService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@CrossOrigin(origins = "https://www.junsang-lotto.me")
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
    public ApiSuccessResponse<Boolean> signup(@RequestBody SignupRequest request) {
        return wrap(authService.signup(UserRole.USER, request));
    }

    /* 관리자 회원가입 */
    @PostMapping("/signup/admin")
    public ApiSuccessResponse<Boolean> signupAdmin(@RequestBody SignupRequest request) {
        return wrap(authService.signup(UserRole.ADMIN, request));
    }

    @PostMapping("/email/code")
    public ApiSuccessResponse<Object> sendCode(@RequestBody @Valid VerifyEmailRequest request) {
        authService.sendAuthCode(request);
        return wrap(null);
    }

    @PostMapping("/email/verify")
    public ApiSuccessResponse<Boolean> verifyEmail(@RequestBody @Valid VerifyAuthRequest request) {
        return wrap(authService.verifyEmail(request));
    }

    @PatchMapping("/password/reset")
    public ApiSuccessResponse<Object> resetPassword(@RequestBody @Valid VerifyEmailRequest request) {
        authService.resetPassword(request);
        return wrap(null);
    }

    @PatchMapping("/{userId}")
    public ApiSuccessResponse<String> updateAuthority() {
        return null;
    }
}

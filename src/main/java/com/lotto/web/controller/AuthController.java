package com.lotto.web.controller;

import com.lotto.web.constants.UserRole;
import com.lotto.web.model.dto.request.SignupRequest;
import com.lotto.web.model.dto.response.common.ApiSuccessResponse;
import com.lotto.web.model.dto.response.common.TokenResponse;
import com.lotto.web.model.entity.UserEntity;
import com.lotto.web.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthController extends BaseController {

    private final AuthService authService;

    /* 로그인 */
    @PostMapping("/login")
    public ApiSuccessResponse<TokenResponse> login() {
        return null;
    }


    /* 일반 사용자 회원가입 */
    @PostMapping("/signup")
    public ApiSuccessResponse<UserEntity> signup(@RequestBody SignupRequest request) {
        return wrap(authService.signup(UserRole.USER, request));
    }

    /* 관리자 회원가입 */
    @PostMapping("/signup/admin")
    public ApiSuccessResponse<UserEntity> signupAdmin() {
        return null;
    }

    @PatchMapping("/{userId}")
    public ApiSuccessResponse<String> updateAuthority() {
        return null;
    }
}

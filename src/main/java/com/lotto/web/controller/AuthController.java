package com.lotto.web.controller;

import com.lotto.web.model.dto.response.common.ApiSuccessResponse;
import com.lotto.web.model.dto.response.common.TokenResponse;
import com.lotto.web.model.entity.UserEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RequestMapping("/api/auth")
@RestController
public class AuthController extends BaseController {

    @GetMapping("/me")
    public ApiSuccessResponse<UserEntity> me() {
        return null;
    }

    /* 로그인 */
    @PostMapping("/login")
    public ApiSuccessResponse<TokenResponse> login() {
        return null;
    }


    /* 일반 사용자 회원가입 */
    @PostMapping("/signup")
    public ApiSuccessResponse<UserEntity> signup() {
        return null;
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

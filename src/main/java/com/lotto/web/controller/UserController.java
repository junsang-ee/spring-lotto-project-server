package com.lotto.web.controller;

import com.lotto.web.model.dto.response.UserDetailResponse;
import com.lotto.web.model.dto.response.common.ApiSuccessResponse;
import com.lotto.web.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
public class UserController extends BaseController {
    private final UserService userService;

    @GetMapping("/me")
    public ApiSuccessResponse<UserDetailResponse> me(@AuthenticationPrincipal(expression = "id") String userId) {
        return wrap(userService.getDetail(userId));
    }
}

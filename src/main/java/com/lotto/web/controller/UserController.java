package com.lotto.web.controller;

import com.lotto.web.model.dto.request.UpdatePasswordRequest;
import com.lotto.web.model.dto.response.UserDetailResponse;
import com.lotto.web.model.dto.response.common.ApiSuccessResponse;
import com.lotto.web.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @PatchMapping("/password")
    public ApiSuccessResponse<Boolean> updatePassword(@AuthenticationPrincipal(expression = "id") String userId,
                                                     @Valid @RequestBody UpdatePasswordRequest request) {
        return wrap(userService.updatePassword(
                userId,
                request.getOldPassword(),
                request.getNewPassword())
        );
    }
}

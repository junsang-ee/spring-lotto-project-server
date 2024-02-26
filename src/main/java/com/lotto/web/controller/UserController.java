package com.lotto.web.controller;

import com.lotto.web.model.dto.request.UpdatePasswordRequest;
import com.lotto.web.model.dto.response.UserDetailResponse;
import com.lotto.web.model.dto.response.common.ApiSuccessResponse;
import com.lotto.web.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public ApiSuccessResponse<Object> updatePassword(@AuthenticationPrincipal(expression = "id") String userId,
                                                     @Valid @RequestBody UpdatePasswordRequest request) {
        userService.updatePassword(
                userId,
                request.getOldPassword(),
                request.getNewPassword()
        );
        return wrap(null);
    }

    @PatchMapping("/retired")
    public ApiSuccessResponse<Object> retired(@AuthenticationPrincipal(expression = "id") String userId) {
        userService.retired(userId);
        return wrap(null);
    }


    @GetMapping("/available-count")
    public ApiSuccessResponse<Integer> getDailyAvailableCount(@AuthenticationPrincipal(expression = "id") String userId) {
        return wrap(userService.getUser(userId).getDailyAvailableCount());
    }
}

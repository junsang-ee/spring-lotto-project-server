package com.lotto.web.controller.admin;


import com.lotto.web.constants.UserStatus;
import com.lotto.web.controller.BaseController;
import com.lotto.web.model.dto.request.BoardSaveRequest;
import com.lotto.web.model.dto.request.SettingUpdateRequest;
import com.lotto.web.model.dto.response.admin.BoardDetailResponse;
import com.lotto.web.model.dto.response.admin.UserDetailResponse;
import com.lotto.web.model.dto.response.common.ApiSuccessResponse;
import com.lotto.web.model.dto.response.common.PageResponse;
import com.lotto.web.model.entity.BoardEntity;
import com.lotto.web.service.BoardService;
import com.lotto.web.service.admin.AdminService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/admin")
@RestController
public class AdminController extends BaseController {

    private final BoardService boardService;

    private final AdminService adminService;

    @PostMapping("/board")
    public ApiSuccessResponse<BoardEntity> saveBoard(@AuthenticationPrincipal(expression = "id") String userId,
                                                     @RequestBody BoardSaveRequest request) {
        return wrap(boardService.save(userId, request));
    }

    @GetMapping("/board")
    public ApiSuccessResponse<List<BoardDetailResponse>> getBoardList() {
        return wrap(adminService.getBoardList());
    }

    @GetMapping("/post/{postId}")
    public ApiSuccessResponse<Object> getPost() {
        return null;
    }

    @DeleteMapping("/board/{boardId}")
    public ApiSuccessResponse<Boolean> deleteBoard(@PathVariable String boardId) {
        return wrap(boardService.deleteBoard(boardId));
    }

    @PutMapping("/setting/lotto-history")
    public ApiSuccessResponse<Object> updateLottoAutomation(@RequestBody SettingUpdateRequest toggle) {
        adminService.updateLottoAutomationSetting(toggle);
        return wrap(null);
    }

    @GetMapping("/user/list")
    public ApiSuccessResponse<PageResponse<UserDetailResponse>> getUserList(Pageable pageable) {
        return page(adminService.getUserList(pageable));
    }

    @PatchMapping("/user/{userId}/status")
    public ApiSuccessResponse<Object> updateUserStatus(@PathVariable("userId") String userId,
                                                       UserStatus status) {
        return null;
    }


}

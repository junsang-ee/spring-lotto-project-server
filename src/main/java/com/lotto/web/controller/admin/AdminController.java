package com.lotto.web.controller.admin;

import com.lotto.web.controller.BaseController;
import com.lotto.web.model.dto.request.*;
import com.lotto.web.model.dto.response.admin.BoardManageDetailResponse;
import com.lotto.web.model.dto.response.admin.PostManageDetailResponse;
import com.lotto.web.model.dto.response.admin.UserManageDetailResponse;
import com.lotto.web.model.dto.response.common.ApiSuccessResponse;
import com.lotto.web.model.dto.response.common.PageResponse;
import com.lotto.web.model.entity.BoardEntity;
import com.lotto.web.service.admin.AdminService;

import com.lotto.web.service.crawler.CrawlerService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;


@RequiredArgsConstructor
@RequestMapping("/api/admin")
@RestController
public class AdminController extends BaseController {

    private final AdminService adminService;

    private final CrawlerService crawlerService;

    @PostMapping("/board")
    public ApiSuccessResponse<BoardEntity> saveBoard(@RequestBody BoardSaveRequest request) {
        return wrap(adminService.saveBoard(request));
    }

    @GetMapping("/boards")
    public ApiSuccessResponse<PageResponse<BoardManageDetailResponse>> getBoardList(Pageable pageable) {
        return page(adminService.getBoardList(pageable));
    }

    @PatchMapping("/board/{boardId}/status")
    public ApiSuccessResponse<Object> updateBoardStatus(@PathVariable String boardId,
                                                        @RequestBody BoardStatusRequest request) {
        adminService.updateBoardStatus(boardId, request.getStatus());
        return wrap(null);
    }

    @PatchMapping("/post/{postId}/status")
    public ApiSuccessResponse<Object> updatePostStatus(@PathVariable String postId,
                                                       @RequestBody PostStatusRequest request) {
        adminService.updatePostStatus(postId, request.getStatus());
        return wrap(null);
    }

    @GetMapping("/board/{boardId}/posts")
    public ApiSuccessResponse<PageResponse<PostManageDetailResponse>> getPosts(@PathVariable String boardId,
                                                                               Pageable pageable) {
        return page(adminService.getPostList(boardId, pageable));
    }

    @GetMapping("/post/{postId}")
    public ApiSuccessResponse<Object> getPost() {
        return null;
    }

    @DeleteMapping("/board/{boardId}")
    public ApiSuccessResponse<Boolean> deleteBoard(@PathVariable String boardId) {
        return wrap(adminService.deleteBoard(boardId));
    }

    @PutMapping("/setting/lotto-history")
    public ApiSuccessResponse<Object> updateLottoAutomation(@RequestBody SettingUpdateRequest toggle) {
        adminService.updateLottoAutomationSetting(toggle);
        return wrap(null);
    }

    @GetMapping("/users")
    public ApiSuccessResponse<PageResponse<UserManageDetailResponse>> getUserList(Pageable pageable) {
        return page(adminService.getUserList(pageable));
    }

    @PatchMapping("/user/{userId}/status")
    public ApiSuccessResponse<Object> updateUserStatus(@PathVariable("userId") String userId,
                                                       @RequestBody UserStatusRequest request) {
        adminService.updateUserStatus(userId, request.getStatus());
        return wrap(null);
    }

    @PostMapping("/update/lotto-winnings")
    public ApiSuccessResponse<Object> updateLottoWinnings() {
        crawlerService.createWinningHistory();
        return wrap(null);
    }



}

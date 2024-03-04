package com.lotto.web.controller.admin;

import com.lotto.web.constants.BoardActivationStatus;
import com.lotto.web.controller.BaseController;
import com.lotto.web.model.dto.request.*;
import com.lotto.web.model.dto.response.BoardDeleteResponse;
import com.lotto.web.model.dto.response.BoardSaveResponse;
import com.lotto.web.model.dto.response.admin.BoardManageDetailResponse;
import com.lotto.web.model.dto.response.admin.PostManageDetailResponse;
import com.lotto.web.model.dto.response.admin.UserManageDetailResponse;
import com.lotto.web.model.dto.response.common.ApiSuccessResponse;
import com.lotto.web.model.dto.response.common.PageResponse;
import com.lotto.web.model.entity.BoardEntity;
import com.lotto.web.service.admin.AdminService;

import com.lotto.web.service.admin.crawler.CrawlerServiceImpl;
import com.lotto.web.service.admin.management.BoardManagementService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;


@RequiredArgsConstructor
@RequestMapping("/api/admin")
@RestController
public class AdminController extends BaseController {

    private final AdminService adminService;

    private final BoardManagementService boardManagementService;

    private final CrawlerServiceImpl crawlerService;

    @PostMapping("/board")
    public ApiSuccessResponse<BoardSaveResponse> saveBoard(@RequestBody BoardSaveRequest request) {
        return wrap(boardManagementService.save(request));
    }

    @GetMapping("/boards")
    public ApiSuccessResponse<PageResponse<BoardManageDetailResponse>> getBoards(Pageable pageable) {
        return page(boardManagementService.list(pageable));
    }

    @DeleteMapping("/board/{boardId}")
    public ApiSuccessResponse<BoardDeleteResponse> deleteBoard(@PathVariable String boardId) {
        return wrap(boardManagementService.delete(boardId));
    }

    @DeleteMapping("/post/{postId}")
    public ApiSuccessResponse<Object> deletePost(@PathVariable String postId) {
        adminService.deletePost(postId);
        return wrap(null);
    }

    @PatchMapping("/board/{boardId}/status/{status}")
    public ApiSuccessResponse<Object> updateBoardStatus(@PathVariable String boardId,
                                                        @PathVariable BoardActivationStatus status) {
        return wrap(boardManagementService.updateStatus(boardId, status));
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

//    @DeleteMapping("/board/{boardId}")
//    public ApiSuccessResponse<Boolean> deleteBoard(@PathVariable String boardId) {
//        return wrap(adminService.deleteBoard(boardId));
//    }

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

    @PostMapping("/lotto/winning")
    public ApiSuccessResponse<Object> saveLottoWinning() {
        crawlerService.createWinningHistory();
        return wrap(null);
    }



}

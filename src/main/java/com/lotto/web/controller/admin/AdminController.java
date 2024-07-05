package com.lotto.web.controller.admin;

import com.lotto.web.constants.BoardActivationStatus;
import com.lotto.web.constants.PostActivationStatus;
import com.lotto.web.constants.UserStatus;
import com.lotto.web.controller.BaseController;
import com.lotto.web.model.dto.request.*;
import com.lotto.web.model.dto.response.*;
import com.lotto.web.model.dto.response.admin.*;
import com.lotto.web.model.dto.response.common.ApiSuccessResponse;
import com.lotto.web.model.dto.response.common.PageResponse;
import com.lotto.web.model.entity.lotto.LottoWinningHistoryEntity;
import com.lotto.web.service.admin.AdminService;

import com.lotto.web.service.admin.management.BoardManagementService;
import com.lotto.web.service.admin.management.LottoManagementService;
import com.lotto.web.service.admin.management.PostManagementService;
import com.lotto.web.service.admin.management.UserManagementService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.util.List;


@RequiredArgsConstructor
@RequestMapping("/api/admin")
@RestController
public class AdminController extends BaseController {

    private final AdminService adminService;
    private final BoardManagementService boardManagementService;
    private final PostManagementService postManagementService;
    private final UserManagementService userManagementService;
    private final LottoManagementService lottoManagementService;

    @PostMapping("/board")
    public ApiSuccessResponse<BoardSaveResponse> saveBoard(@RequestBody BoardSaveRequest request) {
        return wrap(boardManagementService.save(request));
    }

    @GetMapping("/boards")
    public ApiSuccessResponse<PageResponse<BoardManageListResponse>> getBoards(Pageable pageable) {
        return page(boardManagementService.list(pageable));
    }

    @DeleteMapping("/board/{boardId}")
    public ApiSuccessResponse<BoardDeleteResponse> deleteBoard(@PathVariable String boardId) {
        return wrap(boardManagementService.delete(boardId));
    }

    @DeleteMapping("/post/{postId}")
    public ApiSuccessResponse<Boolean> deletePost(@PathVariable String postId) {
        return wrap(postManagementService.delete(postId));
    }

    @PatchMapping("/board/{boardId}/status/{status}")
    public ApiSuccessResponse<Boolean> updateBoardStatus(@PathVariable String boardId,
                                                        @PathVariable BoardActivationStatus status) {
        return wrap(boardManagementService.updateStatus(boardId, status));
    }

    @PatchMapping("/post/{postId}/status/{status}")
    public ApiSuccessResponse<Boolean> updatePostStatus(@PathVariable String postId,
                                                        @PathVariable PostActivationStatus status) {
        return wrap(postManagementService.updateStatus(postId, status));
    }

    @GetMapping("/board/{boardId}/posts")
    public ApiSuccessResponse<PageResponse<PostManageListResponse>> getPosts(@PathVariable String boardId,
                                                                             Pageable pageable) {
        return page(postManagementService.list(boardId, pageable));
    }

    @GetMapping("/user/{userId}/posts")
    public ApiSuccessResponse<PageResponse<UserPostListResponse>> getPostsByUser(@PathVariable String userId,
                                                                                 Pageable pageable) {
        return page(postManagementService.listByUser(userId, pageable));
    }

    @GetMapping("/user/{userId}/extractions")
    public ApiSuccessResponse<PageResponse<ExtractionListResponse>> getExtractions(@PathVariable String userId,
                                                                                   Pageable pageable) {
        return page(lottoManagementService.getExtractionsByUser(userId, pageable));
    }

    @GetMapping("/post/{postId}")
    public ApiSuccessResponse<PostDetailResponse> getPost(@PathVariable String postId) {
        return wrap(postManagementService.detail(postId));
    }

    @PutMapping("/setting/lotto-history")
    public ApiSuccessResponse<Object> updateLottoAutomation(@RequestBody SettingUpdateRequest toggle) {
        adminService.updateLottoAutomationSetting(toggle);
        return wrap(null);
    }

    @GetMapping("/users")
    public ApiSuccessResponse<PageResponse<UserManageListResponse>> getUserList(Pageable pageable) {
        return page(userManagementService.list(pageable));
    }

    @PatchMapping("/user/{userId}/status/{status}")
    public ApiSuccessResponse<Boolean> updateUserStatus(@PathVariable("userId") String userId,
                                                        @PathVariable UserStatus status) {
        return wrap(userManagementService.updateStatus(userId, status));
    }

    @GetMapping("/user/{userId}")
    public ApiSuccessResponse<UserManageDetailResponse> getUser(@PathVariable("userId") String userId) {
        return wrap(userManagementService.getDetail(userId));
    }

    @PostMapping("/lotto/winning/{round}")
    public ApiSuccessResponse<LottoWinningHistoryEntity> saveWinning(@PathVariable int round) {
        return wrap(lottoManagementService.saveWinningByRound(round));
    }

    @PostMapping("/lotto/winning/recent/{recentNumber}")
    public ApiSuccessResponse<List<LottoWinningHistoryEntity>> saveRecentWinnings(@PathVariable int recentNumber) {
        return wrap(lottoManagementService.saveRecentWinnings(recentNumber));
    }

    @PutMapping("/lotto/extraction/winning-result/{extractionId}")
    public ApiSuccessResponse<ExtractionDrawResultResponse> updateWinningStatus(@PathVariable Long extractionId) {
        return wrap(lottoManagementService.matchExtraction(extractionId));
    }


}

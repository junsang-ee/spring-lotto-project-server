package com.lotto.web.controller;

import com.lotto.web.model.dto.request.PostPasswordRequest;
import com.lotto.web.model.dto.request.PostUpdateRequest;
import com.lotto.web.model.dto.request.ReplySaveRequest;
import com.lotto.web.model.dto.response.PostDetailResponse;
import com.lotto.web.model.dto.response.ReplyDetailResponse;
import com.lotto.web.model.dto.response.ReplySaveResponse;
import com.lotto.web.model.dto.response.common.ApiSuccessResponse;
import com.lotto.web.service.PostService;

import com.lotto.web.service.ReplyService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/post")
@RestController
public class PostController extends BaseController {

    private final PostService postService;

    private final ReplyService replyService;

    @GetMapping("/{postId}")
    public ApiSuccessResponse<PostDetailResponse> get(@AuthenticationPrincipal(expression = "id") String userId,
                                                      @PathVariable String postId) {
        return wrap(postService.detail(userId, postId));
    }

    @PatchMapping("/{postId}")
    public ApiSuccessResponse<Boolean> update(@AuthenticationPrincipal(expression = "id") String userId,
                                              @PathVariable String postId,
                                              @RequestBody PostUpdateRequest request) {
        return wrap(postService.update(userId, postId, request));
    }

    @DeleteMapping("/{postId}")
    public ApiSuccessResponse<Boolean> delete(@AuthenticationPrincipal(expression = "id") String userId,
                                              @PathVariable String postId) {
        return wrap(postService.delete(userId, postId));
    }

    @PostMapping("/{postId}/reply")
    public ApiSuccessResponse<ReplySaveResponse> saveReply(@AuthenticationPrincipal(expression = "id") String userId,
                                                           @PathVariable String postId,
                                                           @Valid @RequestBody ReplySaveRequest request) {
        return wrap(replyService.save(userId, postId, request));
    }

    @GetMapping("/{postId}/replies")
    public ApiSuccessResponse<List<ReplyDetailResponse>> replies(@AuthenticationPrincipal(expression = "id") String userId,
                                                                 @PathVariable String postId,
                                                                 Pageable pageable) {
        return wrap(replyService.listForUser(userId, postId, pageable));
    }

    @PostMapping("/{postId}/verify")
    public ApiSuccessResponse<Boolean> verifyPassword(@PathVariable String postId,
                                                      @Valid @RequestBody PostPasswordRequest request) {
        return wrap(postService.verifyPassword(postId, request.getPassword()));

    }
}

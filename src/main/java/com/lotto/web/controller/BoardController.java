package com.lotto.web.controller;

import com.lotto.web.model.dto.request.PostSaveRequest;
import com.lotto.web.model.dto.response.BoardListResponse;
import com.lotto.web.model.dto.response.PostListEntryResponse;
import com.lotto.web.model.dto.response.common.ApiSuccessResponse;
import com.lotto.web.model.dto.response.common.PageResponse;
import com.lotto.web.service.BoardService;
import com.lotto.web.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/board")
@RestController
public class BoardController extends BaseController {

    private final BoardService boardService;

    private final PostService postService;

    @GetMapping
    public ApiSuccessResponse<BoardListResponse> list() {
        return wrap(boardService.listForUser());
    }

    @GetMapping("/{boardId}/post")
    public ApiSuccessResponse<PageResponse<PostListEntryResponse>> postList(@PathVariable String boardId,
                                                                            Pageable pageable) {
        return page(postService.list(boardId, pageable));
    }

    @PostMapping("/{boardId}/post")
    public ApiSuccessResponse<Boolean> savePost(@AuthenticationPrincipal(expression = "id") String userId,
                                                @PathVariable String boardId,
                                                @RequestBody PostSaveRequest request) {
        return wrap(postService.save(userId, boardId, request));
    }

}

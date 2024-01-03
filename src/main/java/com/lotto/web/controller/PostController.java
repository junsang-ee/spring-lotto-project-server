package com.lotto.web.controller;

import com.lotto.web.model.dto.request.PostSaveRequest;
import com.lotto.web.model.dto.response.common.ApiSuccessResponse;
import com.lotto.web.model.entity.PostEntity;
import com.lotto.web.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/post")
@RestController
public class PostController extends BaseController {

    private final PostService postService;

    @GetMapping("/{postId}")
    public ApiSuccessResponse<PostEntity> get(@PathVariable String postId,
                                              @RequestParam(required = false) String password) {

        return null;
    }
}

package com.lotto.web.controller;

import com.lotto.web.model.dto.request.ReplyUpdateRequest;
import com.lotto.web.model.dto.response.common.ApiSuccessResponse;
import com.lotto.web.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/reply")
@RestController
public class ReplyController extends BaseController {

    private final ReplyService replyService;

    @PatchMapping("/{replyId}")
    public ApiSuccessResponse<Boolean> update(@AuthenticationPrincipal(expression = "id") String userId,
                                              @PathVariable String replyId,
                                              @RequestBody ReplyUpdateRequest request) {
        return wrap(replyService.update(userId, replyId, request));
    }

    @DeleteMapping("/{replyId}")
    public ApiSuccessResponse<Boolean> delete(@AuthenticationPrincipal(expression = "id") String userId,
                                              @PathVariable String replyId) {
        return wrap(replyService.delete(userId, replyId));
    }
}

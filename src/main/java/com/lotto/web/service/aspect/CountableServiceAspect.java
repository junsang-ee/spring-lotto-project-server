package com.lotto.web.service.aspect;

import com.lotto.web.constants.countable.CountableType;
import com.lotto.web.model.dto.request.PostSaveRequest;
import com.lotto.web.model.dto.request.ReplySaveRequest;
import com.lotto.web.model.dto.response.PostSaveResponse;
import com.lotto.web.model.dto.response.ReplySaveResponse;
import com.lotto.web.service.countable.CountableService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Aspect
@Component
public class CountableServiceAspect {

    private final CountableService countableService;

    @AfterReturning(value = "execution(* com..ReplyService.save(..)) && args(userId, postId, request)",
            returning = "result", argNames = "userId, postId, request, result")
    public void updateReplyCount(String userId, String postId, ReplySaveRequest request, ReplySaveResponse reply) {
        countableService.updateReplyCount(postId, CountableType.CREATE);
    }

    @AfterReturning(value = "execution(* com..PostService.save(..)) && args(userId, boardId, request)",
            returning = "result", argNames = "userId, boardId, request, result")
    public void updatePostCount(String userId, String boardId, PostSaveRequest request, PostSaveResponse result) {
        countableService.updatePostCount(boardId, CountableType.CREATE);
    }

}

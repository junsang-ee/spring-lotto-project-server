package com.lotto.web.service.aspect;

import com.lotto.web.constants.PostActivationStatus;
import com.lotto.web.constants.countable.CountableType;
import com.lotto.web.model.dto.request.PostSaveRequest;
import com.lotto.web.model.dto.request.ReplySaveRequest;
import com.lotto.web.model.dto.response.PostSaveResponse;
import com.lotto.web.model.dto.response.ReplySaveResponse;
import com.lotto.web.model.entity.BoardEntity;
import com.lotto.web.service.PostService;
import com.lotto.web.service.countable.CountableService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Aspect
@Component
public class CountableServiceAspect {

    private final CountableService countableService;

    private final PostService postService;

    @AfterReturning(value = "execution(* com..ReplyService.save(..)) && args(userId, postId, request)",
            returning = "result", argNames = "userId, postId, request, result")
    public void updateReplyCount(String userId, String postId, ReplySaveRequest request, ReplySaveResponse reply) {
        countableService.updateReplyCount(postId, CountableType.CREATE);
    }

    @AfterReturning(value = "execution(* com..PostService.save(..)) && args(userId, boardId, request)",
            returning = "result", argNames = "userId, boardId, request, result")
    public void addPostCount(String userId, String boardId, PostSaveRequest request, PostSaveResponse result) {
        countableService.updatePostCount(boardId, CountableType.CREATE);
    }

    @AfterReturning(value = "execution(* com..PostManagementService.updateStatus(..)) && args(postId, request)",
            returning = "result", argNames = "postId, request, result")
    public void updatePostCount(String postId, PostActivationStatus request, boolean result) {
        BoardEntity parentBoard = postService.get(postId).getParentBoard();
        if (request == PostActivationStatus.REMOVED)
            countableService.updatePostCount(parentBoard.getId(), CountableType.REMOVE);
        else if (request == PostActivationStatus.DISABLED)
            countableService.updatePostCount(parentBoard.getId(), CountableType.DISABLE);
        else countableService.updatePostCount(parentBoard.getId(), CountableType.CREATE);
    }

}

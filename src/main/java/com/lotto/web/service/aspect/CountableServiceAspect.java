package com.lotto.web.service.aspect;

import com.lotto.web.constants.countable.ReplyCountUpdateType;
import com.lotto.web.model.dto.request.ReplySaveRequest;
import com.lotto.web.model.entity.ReplyEntity;
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
            returning = "ReplyEntity", argNames = "userId, postId, request, ReplyEntity")
    public void updateReplyCount(String userId, String postId, ReplySaveRequest request, ReplyEntity reply) {
        countableService.updateReplyCount(postId, ReplyCountUpdateType.CREATE);
    }
}

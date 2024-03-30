package com.lotto.web.model.dto.response;

import com.lotto.web.constants.PostActivationStatus;
import com.lotto.web.model.entity.ReplyEntity;
import lombok.*;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public class ReplySaveResponse {
    private final String id;
    private final String content;
    private final PostActivationStatus status;
    private final String parentPostTitle;
    private final String writer;

    public static ReplySaveResponse of(final ReplyEntity reply) {
        return new ReplySaveResponse(
                reply.getId(),
                reply.getContent(),
                reply.getStatus(),
                reply.getParentPost().getTitle(),
                reply.getCreatedBy().getEmail()
        );
    }

}

package com.lotto.web.model.dto.response;

import com.lotto.web.model.entity.ReplyEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public class ReplyDetailResponse {
    private final String id;
    private final String writer;
    private final String content;
    private final boolean mine;

    public static ReplyDetailResponse of(final ReplyEntity reply,
                                         final String userId) {
        return new ReplyDetailResponse(
                reply.getId(),
                reply.getCreatedBy().getEmail(),
                reply.getContent(),
                Objects.equals(reply.getCreatedBy().getId(), userId)
        );
    }
}

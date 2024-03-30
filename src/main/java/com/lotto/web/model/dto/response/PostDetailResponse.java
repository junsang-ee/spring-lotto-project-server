package com.lotto.web.model.dto.response;

import com.lotto.web.constants.PostDisclosureType;
import com.lotto.web.model.entity.PostEntity;
import lombok.*;

import java.util.Objects;

import static lombok.AccessLevel.PRIVATE;
@Getter
@RequiredArgsConstructor(access = PRIVATE)
public class PostDetailResponse {

    private final String writer;
    private final String title;
    private final String content;
    private final PostDisclosureType disclosureType;
    private final int replyCount;
    private final boolean mine;

    public static PostDetailResponse of(final String requestUserId, final PostEntity post) {
        return new PostDetailResponse(
                post.getCreatedBy().getEmail(),
                post.getTitle(),
                post.getContent(),
                post.getDisclosureType(),
                post.getReplyCount().getEnabledCount(),
                requestUserId.equals(post.getCreatedBy().getId())
        );
    }

}

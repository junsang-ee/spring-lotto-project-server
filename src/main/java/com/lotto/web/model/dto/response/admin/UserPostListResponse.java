package com.lotto.web.model.dto.response.admin;

import com.lotto.web.constants.PostActivationStatus;
import com.lotto.web.constants.PostDisclosureType;
import com.lotto.web.model.entity.PostEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public class UserPostListResponse {
    private final String parentBoardId;
    private final String postId;
    private final String parentBoardName;
    private final String title;
    private final PostActivationStatus status;
    private final PostDisclosureType disclosureType;
    private final long replyCount;
    private final Instant createdAt;

    public static UserPostListResponse of(final PostEntity post) {
        return new UserPostListResponse(
                post.getParentBoard().getId(),
                post.getId(),
                post.getParentBoard().getName(),
                post.getTitle(),
                post.getStatus(),
                post.getDisclosureType(),
                post.getReplyCount().getEnabledCount(),
                post.getCreatedAt()
        );
    }
}

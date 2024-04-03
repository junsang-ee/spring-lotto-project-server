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
public class PostManageListResponse {

    private final String id;
    private final String title;
    private final String writer;
    private final PostDisclosureType disclosureType;
    private final PostActivationStatus status;
    private final int enabledReplyCount;
    private final int disabledReplyCount;
    private final Instant createdAt;

    public static PostManageListResponse of(final PostEntity post) {
        return new PostManageListResponse(
                post.getId(),
                post.getTitle(),
                post.getCreatedBy().getEmail(),
                post.getDisclosureType(),
                post.getStatus(),
                post.getReplyCount().getEnabledCount(),
                post.getReplyCount().getDisabledCount(),
                post.getCreatedAt()
        );
    }
}

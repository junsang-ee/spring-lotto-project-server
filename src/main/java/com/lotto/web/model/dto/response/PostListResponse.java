package com.lotto.web.model.dto.response;

import com.lotto.web.constants.PostDisclosureType;
import com.lotto.web.model.entity.PostEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public class PostListResponse {
    private final String id;
    private final String title;
    private final PostDisclosureType disclosureType;
    private final String email;
    private final Instant createdAt;
    private final Integer viewCount;

    public static PostListResponse of(final PostEntity post) {
        return new PostListResponse(
                post.getId(),
                post.getTitle(),
                post.getDisclosureType(),
                post.getCreatedBy().getEmail(),
                post.getCreatedAt(),
                post.getViewCount()
        );
    }

}

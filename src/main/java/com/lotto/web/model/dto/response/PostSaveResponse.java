package com.lotto.web.model.dto.response;

import com.lotto.web.constants.PostActivationStatus;
import com.lotto.web.constants.PostDisclosureType;
import com.lotto.web.model.entity.PostEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public class PostSaveResponse {
    private final String id;
    private final String title;
    private final String content;
    private final int viewCount;
    private final PostActivationStatus status;
    private final PostDisclosureType disclosureType;
    private final String writer;

    public static PostSaveResponse of(final PostEntity post) {
        return new PostSaveResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getViewCount(),
                post.getStatus(),
                post.getDisclosureType(),
                post.getCreatedBy().getEmail()
        );
    }

}

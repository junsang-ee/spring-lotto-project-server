package com.lotto.web.model.dto.response;

import com.lotto.web.constants.PostEditableType;
import com.lotto.web.constants.PostDisclosureType;
import com.lotto.web.model.entity.PostEntity;
import lombok.*;

import static lombok.AccessLevel.PRIVATE;
@Getter
@RequiredArgsConstructor(access = PRIVATE)
public class PostDetailResponse {

    private final String writer;
    private final String title;
    private final String content;
    private final PostDisclosureType disclosureType;
    private final int replyCount;
    private final PostEditableType postEditableType;

    public static PostDetailResponse of(final String requestUserId, final PostEntity post, final boolean isAdmin) {
        return new PostDetailResponse(
                post.getCreatedBy().getEmail(),
                post.getTitle(),
                post.getContent(),
                post.getDisclosureType(),
                post.getReplyCount().getEnabledCount(),
                getPostEditAuthority(requestUserId, post, isAdmin)
        );
    }

    protected static PostEditableType getPostEditAuthority(String requestUserId, PostEntity post, boolean isAdmin) {
        if (isAdmin) return PostEditableType.EDITABLE;
        String writerId = post.getCreatedBy().getId();
        if (requestUserId.equals(writerId))
            return PostEditableType.EDITABLE;
        return PostEditableType.NOT_EDITABLE;
    }

}

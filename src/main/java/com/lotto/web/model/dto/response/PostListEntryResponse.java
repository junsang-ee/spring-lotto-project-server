package com.lotto.web.model.dto.response;

import com.lotto.web.constants.PostDisclosureType;
import com.lotto.web.model.querydsl.PostListQueryResult;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class PostListEntryResponse {
    private String title;
    private PostDisclosureType disclosureType;
    private String email;
    private Instant createdAt;
    private int viewCount;

    public PostListEntryResponse(PostListQueryResult result) {
        this.title = result.getTitle();
        this.disclosureType = result.getDisclosureType();
        this.email = result.getEmail();
        this.createdAt = result.getCreatedAt();
        this.viewCount = result.getViewCount();
    }
}

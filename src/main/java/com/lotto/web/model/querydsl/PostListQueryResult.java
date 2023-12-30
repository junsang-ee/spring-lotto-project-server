package com.lotto.web.model.querydsl;

import com.lotto.web.constants.PostDisclosureType;

import java.time.Instant;

public interface PostListQueryResult {
    String getTitle();

    PostDisclosureType getDisclosureType();

    String getEmail();

    Instant getCreatedAt();

    int getViewCount();

}

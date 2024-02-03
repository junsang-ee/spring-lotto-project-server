package com.lotto.web.model.querydsl;

import java.time.Instant;

public interface PostListQueryResult {
    String getTitle();

    String getDisclosureType();

    String getEmail();

    Instant getCreatedAt();

    Integer getViewCount();

}

package com.lotto.web.model.dto.response;

import lombok.*;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public class PostDeleteResponse {
    private final String title;

    public static PostDeleteResponse of(final String title) {
        return new PostDeleteResponse(title);
    }
}

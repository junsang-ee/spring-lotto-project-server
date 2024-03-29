package com.lotto.web.model.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PostListResponse {
    private List<PostListEntryResponse> posts;
}

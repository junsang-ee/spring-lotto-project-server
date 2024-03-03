package com.lotto.web.model.dto.response;

import com.lotto.web.constants.PostDisclosureType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
public class PostListEntryResponse {
    private String id;
    private String title;
    private PostDisclosureType disclosureType;
    private String email;
    private Instant createdAt;
    private Integer viewCount;
}

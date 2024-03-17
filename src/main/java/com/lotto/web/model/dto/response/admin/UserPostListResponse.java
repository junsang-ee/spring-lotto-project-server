package com.lotto.web.model.dto.response.admin;

import com.lotto.web.constants.PostActivationStatus;
import com.lotto.web.constants.PostDisclosureType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class UserPostListResponse {
    private String parentBoardName;
    private String title;
    private PostActivationStatus status;
    private PostDisclosureType disclosureType;
    private long replyCount;
    private Instant createdAt;
}

package com.lotto.web.model.dto.response.admin;

import com.lotto.web.constants.UserStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@NoArgsConstructor
public class UserManageDetailResponse {
    private String email;

    private UserStatus status;

    private int dailyAvailableCount;

    private long postCount;

    private long extractionCount;

    private Instant createdAt;
}

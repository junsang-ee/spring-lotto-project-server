package com.lotto.web.model.dto.response.admin;

import com.lotto.web.constants.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class UserManageDetailResponse {
    private String email;

    private UserStatus status;

    private int dailyAvailableCount;

    private long postCount;

    private long extractionCount;

    private long winningCount;

    private Instant createdAt;
}

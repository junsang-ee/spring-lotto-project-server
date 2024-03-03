package com.lotto.web.model.dto.response.admin;

import com.lotto.web.constants.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
public class UserManageDetailResponse {

    private String id;

    private String email;

    private UserStatus status;

    private int dailyAvailableCount;

    private long postCount;

    private Instant createdAt;
}

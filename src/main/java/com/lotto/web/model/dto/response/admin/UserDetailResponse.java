package com.lotto.web.model.dto.response.admin;

import com.lotto.web.constants.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDetailResponse {

    private String id;

    private String email;

    private UserStatus status;

    private int dailyAvailableCount;

    private long postCount;
}

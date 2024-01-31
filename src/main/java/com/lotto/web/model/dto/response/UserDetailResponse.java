package com.lotto.web.model.dto.response;

import com.lotto.web.constants.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class UserDetailResponse {

    private String email;

    private Instant createdAt;

    private UserRole role;

}

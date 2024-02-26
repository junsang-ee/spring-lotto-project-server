package com.lotto.web.model.dto.request;

import com.lotto.web.constants.UserStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserStatusRequest {
    private UserStatus status;
}

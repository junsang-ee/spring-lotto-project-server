package com.lotto.web.model.dto.response;

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

}

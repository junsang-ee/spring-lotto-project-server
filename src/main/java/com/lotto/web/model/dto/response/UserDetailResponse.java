package com.lotto.web.model.dto.response;

import com.lotto.web.constants.UserRole;
import com.lotto.web.model.entity.UserEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public class UserDetailResponse {

    private final String email;

    private final Instant createdAt;

    private final UserRole role;

    public static UserDetailResponse of(final UserEntity user) {
        return new UserDetailResponse(
                user.getEmail(),
                user.getCreatedAt(),
                user.getRole()
        );
    }

}

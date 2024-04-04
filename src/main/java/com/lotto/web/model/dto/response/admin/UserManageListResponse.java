package com.lotto.web.model.dto.response.admin;

import com.lotto.web.constants.UserStatus;
import com.lotto.web.model.entity.UserEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

import static lombok.AccessLevel.PRIVATE;
@Getter
@RequiredArgsConstructor(access = PRIVATE)
public class UserManageListResponse {
    private final String id;
    private final String email;
    private final UserStatus status;
    private final int dailyAvailableCount;
    private final int postCount;
    private final Instant createdAt;

    public static UserManageListResponse of(final UserEntity user) {
        return new UserManageListResponse(
                user.getId(),
                user.getEmail(),
                user.getStatus(),
                user.getDailyAvailableCount(),
                user.getPosts().size(),
                user.getCreatedAt()
        );
    }
}

package com.lotto.web.model.dto.response.admin;

import com.lotto.web.constants.UserStatus;
import com.lotto.web.constants.WinningStatus;
import com.lotto.web.model.entity.UserEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

import static lombok.AccessLevel.PRIVATE;
@Getter
@RequiredArgsConstructor(access = PRIVATE)
public class UserManageDetailResponse {
    private final String email;
    private final UserStatus status;
    private final int dailyAvailableCount;
    private final int postCount;
    private final int extractionCount;
    private final int winningCount;
    private final Instant createdAt;

    public static UserManageDetailResponse of(final UserEntity user) {
        int winningCount = (int) user.getExtractionLottoList()
                .stream().filter(
                        e -> e.getWinningStatus().getOverallStatus().equals(WinningStatus.WON)
                ).count();
        return new UserManageDetailResponse(
                user.getEmail(),
                user.getStatus(),
                user.getDailyAvailableCount(),
                user.getPosts().size(),
                user.getExtractionLottoList().size(),
                winningCount,
                user.getCreatedAt()
        );
    }
}

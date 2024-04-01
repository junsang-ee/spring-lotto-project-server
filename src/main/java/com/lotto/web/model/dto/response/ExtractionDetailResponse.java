package com.lotto.web.model.dto.response;

import com.lotto.web.constants.WinningStatus;
import com.lotto.web.model.entity.lotto.ExtractionHistoryEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public class ExtractionDetailResponse {
    private final int firstNumber;
    private final int secondNumber;
    private final int thirdNumber;
    private final int fourthNumber;
    private final int fifthNumber;
    private final int sixthNumber;
    private final WinningStatus firstStatus;
    private final WinningStatus secondStatus;
    private final WinningStatus thirdStatus;
    private final WinningStatus fourthStatus;
    private final WinningStatus fifthStatus;
    private final WinningStatus sixthStatus;
    private final WinningStatus winningResult;
    private final Instant createdAt;

    public static ExtractionDetailResponse of(final ExtractionHistoryEntity entity) {
        return new ExtractionDetailResponse(
                entity.getFirstNumber(),
                entity.getSecondNumber(),
                entity.getThirdNumber(),
                entity.getFourthNumber(),
                entity.getFifthNumber(),
                entity.getSixthNumber(),
                entity.getWinningStatus().getFirstStatus(),
                entity.getWinningStatus().getSecondStatus(),
                entity.getWinningStatus().getThirdStatus(),
                entity.getWinningStatus().getFourthStatus(),
                entity.getWinningStatus().getFifthStatus(),
                entity.getWinningStatus().getSixthStatus(),
                entity.getWinningStatus().getOverallStatus(),
                entity.getCreatedAt()
        );
    }


}

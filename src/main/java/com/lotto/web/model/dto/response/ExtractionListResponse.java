package com.lotto.web.model.dto.response;

import com.lotto.web.constants.MatchStatus;
import com.lotto.web.constants.WinningResultType;
import com.lotto.web.model.entity.lotto.ExtractionHistoryEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public class ExtractionListResponse {
    private final int firstNumber;
    private final int secondNumber;
    private final int thirdNumber;
    private final int fourthNumber;
    private final int fifthNumber;
    private final int sixthNumber;
    private final MatchStatus firstStatus;
    private final MatchStatus secondStatus;
    private final MatchStatus thirdStatus;
    private final MatchStatus fourthStatus;
    private final MatchStatus fifthStatus;
    private final MatchStatus sixthStatus;
    private final WinningResultType winningResult;
    private final Instant createdAt;

    public static ExtractionListResponse of(final ExtractionHistoryEntity entity) {
        return new ExtractionListResponse(
                entity.getFirstNumber(), entity.getSecondNumber(),
                entity.getThirdNumber(), entity.getFourthNumber(),
                entity.getFifthNumber(), entity.getSixthNumber(),
                entity.getWinningStatus().getFirstStatus(),
                entity.getWinningStatus().getSecondStatus(),
                entity.getWinningStatus().getThirdStatus(),
                entity.getWinningStatus().getFourthStatus(),
                entity.getWinningStatus().getFifthStatus(),
                entity.getWinningStatus().getSixthStatus(),
                entity.getWinningStatus().getWinningResult(),
                entity.getCreatedAt()
        );
    }


}

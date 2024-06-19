package com.lotto.web.model.dto.response;


import com.lotto.web.constants.MatchStatus;
import com.lotto.web.constants.WinningResultType;
import com.lotto.web.model.entity.lotto.WinningStatusEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static lombok.AccessLevel.PRIVATE;


@Getter
@RequiredArgsConstructor(access = PRIVATE)
public class ExtractionDrawResultResponse {
    private final MatchStatus firstStatus;
    private final MatchStatus secondStatus;
    private final MatchStatus thirdStatus;
    private final MatchStatus fourthStatus;
    private final MatchStatus fifthStatus;
    private final MatchStatus sixthStatus;
    private final WinningResultType winningResult;

    public static ExtractionDrawResultResponse of(final WinningStatusEntity winningStatus) {
        return new ExtractionDrawResultResponse(
                winningStatus.getFirstStatus(),
                winningStatus.getSecondStatus(),
                winningStatus.getThirdStatus(),
                winningStatus.getFourthStatus(),
                winningStatus.getFifthStatus(),
                winningStatus.getSixthStatus(),
                winningStatus.getWinningResult()
        );
    }

}

package com.lotto.web.model.dto.response;

import com.lotto.web.model.entity.lotto.LottoWinningHistoryEntity;
import lombok.*;

import static lombok.AccessLevel.PRIVATE;
@Getter
@RequiredArgsConstructor(access = PRIVATE)
public class LottoWinningNumbersResponse {
    private final int firstNumber;
    private final int secondNumber;
    private final int thirdNumber;
    private final int fourthNumber;
    private final int fifthNumber;
    private final int sixthNumber;
    private final int bonusNumber;

    public static LottoWinningNumbersResponse of(final LottoWinningHistoryEntity lottoWinningHistory) {
        return new LottoWinningNumbersResponse(
                lottoWinningHistory.getFirstNumber(),
                lottoWinningHistory.getSecondNumber(),
                lottoWinningHistory.getThirdNumber(),
                lottoWinningHistory.getFourthNumber(),
                lottoWinningHistory.getFifthNumber(),
                lottoWinningHistory.getSixthNumber(),
                lottoWinningHistory.getBonusNumber()
        );
    }

}

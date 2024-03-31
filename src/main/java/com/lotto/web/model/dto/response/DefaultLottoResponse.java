package com.lotto.web.model.dto.response;

import com.lotto.web.model.vo.LottoVO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import static lombok.AccessLevel.PRIVATE;
@Getter
@RequiredArgsConstructor(access = PRIVATE)
public class DefaultLottoResponse {
    private final int firstNumber;
    private final int secondNumber;
    private final int thirdNumber;
    private final int fourthNumber;
    private final int fifthNumber;
    private final int sixthNumber;

    public static DefaultLottoResponse of(LottoVO lottoVO) {
        return new DefaultLottoResponse(
            lottoVO.getLottoList().get(0),
            lottoVO.getLottoList().get(1),
            lottoVO.getLottoList().get(2),
            lottoVO.getLottoList().get(3),
            lottoVO.getLottoList().get(4),
            lottoVO.getLottoList().get(5)
        );

    }
}

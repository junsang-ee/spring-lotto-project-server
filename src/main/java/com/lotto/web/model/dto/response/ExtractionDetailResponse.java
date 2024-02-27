package com.lotto.web.model.dto.response;

import com.lotto.web.constants.WinningStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
public class ExtractionDetailResponse {
    private int firstNumber;
    private int secondNumber;
    private int thirdNumber;
    private int fourthNumber;
    private int fifthNumber;
    private int sixthNumber;
    private WinningStatus firstStatus;
    private WinningStatus secondStatus;
    private WinningStatus thirdStatus;
    private WinningStatus fourthStatus;
    private WinningStatus fifthStatus;
    private WinningStatus sixthStatus;
    private WinningStatus winningResult;
    private Instant createdAt;
}

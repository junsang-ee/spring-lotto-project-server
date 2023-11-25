package com.lotto.web.model.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DefaultLottoResponse {
    private int firstNumber;
    private int secondNumber;
    private int thirdNumber;
    private int fourthNumber;
    private int fifthNumber;
    private int sixthNumber;
}

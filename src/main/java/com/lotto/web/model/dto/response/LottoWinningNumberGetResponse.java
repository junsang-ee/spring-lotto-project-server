package com.lotto.web.model.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LottoWinningNumberGetResponse {
    private LottoGetResponse winningNumber;
    private int bonusNumber;
}

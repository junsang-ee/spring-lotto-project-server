package com.lotto.web.service;

import com.lotto.web.model.dto.request.LottoListRequest;
import com.lotto.web.model.dto.response.DefaultLottoListResponse;
import com.lotto.web.model.dto.response.LottoWinningNumbersResponse;

import java.util.Date;

public interface LottoService {

    DefaultLottoListResponse getRandomList(LottoListRequest request);

    LottoWinningNumbersResponse getWinningNumbersByRound(int round);

    LottoWinningNumbersResponse getWinningNumbersByDrawDate(Date drawDate);

    void saveWinningNumbers();
}

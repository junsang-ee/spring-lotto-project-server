package com.lotto.web.service;

import com.lotto.web.model.dto.request.LottoListGetRequest;
import com.lotto.web.model.dto.response.DefaultLottoListResponse;
import com.lotto.web.model.dto.response.LottoWinningNumbersResponse;

import java.util.Date;

public interface LottoService {

    DefaultLottoListResponse getRandomList(LottoListGetRequest request);

    LottoWinningNumbersResponse getWinningNumbersByRound(int round);

    LottoWinningNumbersResponse getWinningNumbersByDrawDate(Date drawDate);

    void saveWinningNumbers();
}

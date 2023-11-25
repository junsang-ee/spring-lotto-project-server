package com.lotto.web.service;

import com.lotto.web.model.dto.request.LottoListGetRequest;
import com.lotto.web.model.dto.response.DefaultLottoListResponse;
import com.lotto.web.model.dto.response.LottoWinningNumberGetResponse;

import java.util.Date;

public interface LottoService {

    DefaultLottoListResponse getRandomList(LottoListGetRequest request);

    LottoWinningNumberGetResponse getWinningNumbersByRound(int round);

    LottoWinningNumberGetResponse getWinningNumbersByDrawDate(Date drawDate);

    void saveWinningNumbers();
}

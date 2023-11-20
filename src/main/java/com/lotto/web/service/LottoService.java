package com.lotto.web.service;

import com.lotto.web.model.dto.request.LottoListGetRequest;
import com.lotto.web.model.dto.response.LottoListGetResponse;
import com.lotto.web.model.dto.response.LottoWinningNumberGetResponse;

import java.util.Date;

public interface LottoService {

    LottoListGetResponse getRandomList(LottoListGetRequest request);

    LottoWinningNumberGetResponse getWinningNumbers(int round);

    LottoWinningNumberGetResponse getWinningNumbers(Date roundDate);
}

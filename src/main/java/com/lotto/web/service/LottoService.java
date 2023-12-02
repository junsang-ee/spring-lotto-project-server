package com.lotto.web.service;

import com.lotto.web.model.dto.request.LottoListRequest;
import com.lotto.web.model.dto.response.DefaultLottoListResponse;
import com.lotto.web.model.dto.response.LottoWinningNumbersResponse;
import com.lotto.web.model.entity.LottoHistoryEntity;

import java.util.Date;
import java.util.List;

public interface LottoService {

    DefaultLottoListResponse getRandomList(int price, List<Integer> exceptList, List<Integer> needsList);

    LottoWinningNumbersResponse getWinningNumbersByRound(int round);

    LottoWinningNumbersResponse getWinningNumbersByDrawDate(Date drawDate);

    void saveWinningNumbers();

    List<LottoHistoryEntity> getAllWinningNumbers();
}

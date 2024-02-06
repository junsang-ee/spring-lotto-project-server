package com.lotto.web.service;

import com.lotto.web.model.dto.response.RandomLottoListResponse;
import com.lotto.web.model.dto.response.LottoWinningNumbersResponse;
import com.lotto.web.model.entity.lotto.LottoWinningHistoryEntity;

import java.util.Date;
import java.util.List;

public interface LottoService {

    RandomLottoListResponse getRandomList(String userId, int price, List<Integer> exceptList, List<Integer> needsList);

    LottoWinningNumbersResponse getWinningNumbersByRound(int round);

    LottoWinningNumbersResponse getWinningNumbersByDrawDate(Date drawDate);

    void saveWinningNumbers();

    List<LottoWinningHistoryEntity> getAllWinningNumbers();
}

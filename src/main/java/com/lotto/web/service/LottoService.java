package com.lotto.web.service;

import com.lotto.web.model.dto.response.ExtractionListResponse;
import com.lotto.web.model.dto.response.RandomLottoListResponse;
import com.lotto.web.model.dto.response.LottoWinningNumbersResponse;
import com.lotto.web.model.entity.lotto.LottoWinningHistoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface LottoService {

    RandomLottoListResponse getRandomList(String userId, int price, List<Integer> exceptList, List<Integer> needsList);

    void saveExtractionLottos(String userId, RandomLottoListResponse randomLottos);

    LottoWinningNumbersResponse getWinningNumbersByRound(int round);

    LottoWinningNumbersResponse getWinningNumbersByDrawDate(Date drawDate);

    Page<LottoWinningHistoryEntity> getAllWinningNumbers(Pageable pageable);

    Page<ExtractionListResponse> getAllExtractions(String userId, Pageable pageable);
}

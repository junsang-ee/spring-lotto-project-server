package com.lotto.web.service.admin.management;

import com.lotto.web.constants.messages.ErrorMessage;
import com.lotto.web.exception.custom.DuplicatedException;
import com.lotto.web.exception.custom.InvalidStateException;
import com.lotto.web.model.dto.api.LottoApiResponse;
import com.lotto.web.model.entity.lotto.LottoWinningHistoryEntity;
import com.lotto.web.repository.LottoWinningHistoryRepository;
import com.lotto.web.util.LottoUtil;
import com.lotto.web.util.WebClientUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LottoManagementServiceImpl implements LottoManagementService {

    private final WebClientUtil webClientUtil;

    private final LottoWinningHistoryRepository lottoWinningHistoryRepository;

    @Override
    @Transactional
    public LottoWinningHistoryEntity saveWinningByRound(int round) {
        if (lottoWinningHistoryRepository.findByRound(round).isPresent()) {
            throw new DuplicatedException(ErrorMessage.LOTTO_DUPLICATED_ROUND);
        }

        LottoApiResponse winning = webClientUtil.get(
                LottoUtil.getLottoApiUri(round),
                LottoApiResponse.class
        ).block();

        if (winning == null) {
            throw new InvalidStateException(ErrorMessage.LOTTO_INVALID_ROUND);
        }
        LottoWinningHistoryEntity winningEntity = LottoWinningHistoryEntity.of(winning);
        return lottoWinningHistoryRepository.save(winningEntity);
    }


}

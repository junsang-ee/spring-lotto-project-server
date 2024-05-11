package com.lotto.web.service.admin.management;

import com.lotto.web.constants.messages.ErrorMessage;
import com.lotto.web.exception.custom.DuplicatedException;
import com.lotto.web.exception.custom.InvalidStateException;
import com.lotto.web.exception.custom.NotFoundException;
import com.lotto.web.model.dto.api.LottoApiResponse;
import com.lotto.web.model.dto.response.ExtractionListResponse;
import com.lotto.web.model.entity.UserEntity;
import com.lotto.web.model.entity.lotto.LottoWinningHistoryEntity;
import com.lotto.web.repository.ExtractionHistoryRepository;
import com.lotto.web.repository.LottoWinningHistoryRepository;
import com.lotto.web.repository.UserRepository;
import com.lotto.web.util.LottoUtil;
import com.lotto.web.util.WebClientUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LottoManagementServiceImpl implements LottoManagementService {

    private final WebClientUtil webClientUtil;
    private final UserRepository userRepository;
    private final LottoWinningHistoryRepository lottoWinningHistoryRepository;
    private final ExtractionHistoryRepository extractionHistoryRepository;

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

    @Override
    public Page<ExtractionListResponse> getExtractionsByUser(String userId, Pageable pageable) {
        UserEntity user = getUser(userId);
        return extractionHistoryRepository.findAllByCreatedBy(
                user,
                pageable
        ).map(ExtractionListResponse::of);
    }

    private UserEntity getUser(String userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException(ErrorMessage.USER_NOT_FOUND)
        );
    }
}

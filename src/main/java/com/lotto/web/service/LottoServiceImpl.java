package com.lotto.web.service;

import com.lotto.web.constants.messages.ErrorMessage;
import com.lotto.web.exception.custom.NotFoundException;
import com.lotto.web.model.dto.response.DefaultLottoResponse;
import com.lotto.web.model.dto.response.ExtractionDetailResponse;
import com.lotto.web.model.dto.response.RandomLottoListResponse;
import com.lotto.web.model.dto.response.LottoWinningNumbersResponse;
import com.lotto.web.model.entity.UserEntity;
import com.lotto.web.model.entity.lotto.LottoWinningHistoryEntity;
import com.lotto.web.model.vo.LottoVO;
import com.lotto.web.repository.ExtractionHistoryRepository;
import com.lotto.web.repository.LottoHistoryRepository;
import com.lotto.web.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.lotto.web.util.LottoUtil.*;

@RequiredArgsConstructor
@Service
public class LottoServiceImpl implements LottoService {

    private final LottoVO lottoVO;

    private final UserRepository userRepository;

    private final LottoHistoryRepository lottoHistoryRepository;

    private final ExtractionHistoryRepository extractionHistoryRepository;

    @Override
    public RandomLottoListResponse getRandomList(String userId,
                                                 int price,
                                                 List<Integer> exceptList,
                                                 List<Integer> needsList) {
        return RandomLottoListResponse.of(
                getLottoListResponse(
                        price,
                        exceptList,
                        needsList
                )
        );
    }

    @Override
    public void saveWinningNumbers() {

    }

    @Override
    public LottoWinningNumbersResponse getWinningNumbersByRound(int round) {
        LottoWinningHistoryEntity entity = lottoHistoryRepository.findByRound(round);
        return LottoWinningNumbersResponse.of(entity);
    }

    @Override
    public LottoWinningNumbersResponse getWinningNumbersByDrawDate(Date drawDate) {
        LottoWinningHistoryEntity entity = lottoHistoryRepository.findByDrawDate(drawDate);
        return LottoWinningNumbersResponse.of(entity);
    }

    @Override
    public Page<LottoWinningHistoryEntity> getAllWinningNumbers(Pageable pageable) {
        return lottoHistoryRepository.findAll(pageable);
    }

    @Override
    public Page<ExtractionDetailResponse> getAllExtractions(String userId, Pageable pageable) {
        UserEntity user = getUser(userId);
        return extractionHistoryRepository.findAllByCreatedBy(
                user,
                pageable
        ).map(ExtractionDetailResponse::of);
    }

    private void setLotto(List<Integer> exceptList, List<Integer> needsList) {
        if (!lottoVO.getIsEmpty() || lottoVO.getIsNullable()) {
            lottoVO.resetLottoNumbers();
        }
        if (needsList != null && !needsList.isEmpty()) {
            lottoVO.setNeedsNumbers(needsList);
        }
        while (lottoVO.getLottoList().size() < 6) {
            addLottoNumber(exceptList);
        }
        lottoVO.sort();
    }

    private void addLottoNumber(List<Integer> exceptList) {
        int randomNumber = getRandomNumber();
        if (!lottoVO.getIsDuplicated(randomNumber) && !getIsExcept(exceptList, randomNumber)) {
            lottoVO.addNumber(randomNumber);
        }
    }


    private boolean getIsExcept(List<Integer> exceptList, int randomNumber) {
        if (exceptList == null || exceptList.isEmpty()) return false;
        return exceptList.contains(randomNumber);
    }

    private List<DefaultLottoResponse> getLottoListResponse(int price,
                                                            List<Integer> exceptList,
                                                            List<Integer> needsList) {
        return IntStream.range(0, getLottoCount(price))
                .mapToObj(index -> {
                    setLotto(exceptList, needsList);
                    return DefaultLottoResponse.of(lottoVO);
                }).collect(Collectors.toList());
    }


    private UserEntity getUser(String userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException(ErrorMessage.USER_NOT_FOUND)
        );
    }

}

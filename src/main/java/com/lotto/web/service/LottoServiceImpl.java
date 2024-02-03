package com.lotto.web.service;

import com.lotto.web.model.dto.response.DefaultLottoResponse;
import com.lotto.web.model.dto.response.RandomLottoListResponse;
import com.lotto.web.model.dto.response.LottoWinningNumbersResponse;
import com.lotto.web.model.entity.lotto.LottoWinningHistoryEntity;
import com.lotto.web.model.vo.LottoVO;
import com.lotto.web.repository.LottoHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static com.lotto.web.util.LottoUtil.*;

@RequiredArgsConstructor
@Service
public class LottoServiceImpl implements LottoService{

    private final LottoVO lottoVO;

    private final LottoHistoryRepository lottoHistoryRepository;

    @Override
    public RandomLottoListResponse getRandomList(int price,
                                                 List<Integer> exceptList,
                                                 List<Integer> needsList) {
        if (!getIsCorrectPrice(price)) return null;
        RandomLottoListResponse result = new RandomLottoListResponse();
        setLottoListResponse(price, exceptList, needsList, result);
        return result;
    }

    @Override
    public void saveWinningNumbers() {

    }

    @Override
    public LottoWinningNumbersResponse getWinningNumbersByRound(int round) {
        LottoWinningHistoryEntity entity = lottoHistoryRepository.findByRound(round);
        LottoWinningNumbersResponse response = new LottoWinningNumbersResponse();
        setWinningNumbersResponse(entity, response);
        return response;
    }

    @Override
    public LottoWinningNumbersResponse getWinningNumbersByDrawDate(Date drawDate) {
        LottoWinningHistoryEntity entity = lottoHistoryRepository.findByDrawDate(drawDate);
        LottoWinningNumbersResponse response = new LottoWinningNumbersResponse();
        setWinningNumbersResponse(entity, response);
        return response;
    }

    @Override
    public List<LottoWinningHistoryEntity> getAllWinningNumbers() {
        return lottoHistoryRepository.findAll();
    }

    private void setLotto(List<Integer> exceptList, List<Integer> needsList) {
        if (!lottoVO.getLottoList().isEmpty()) {
            lottoVO.resetLottoNumbers();
        }
        if (needsList != null && !needsList.isEmpty()) {
            lottoVO.setNeedsNumbers(needsList);
        }
        while (lottoVO.getLottoList().size() < 6) {
            addLottoNumber(exceptList);
        }
        sortLottoList();
    }

    private void addLottoNumber(List<Integer> exceptList) {
        if (lottoVO.getLottoList() != null) {
            int randomNumber = getRandomNumber();
            if (!getIsDuplicated(randomNumber) && !getIsExcept(exceptList, randomNumber)) {
                lottoVO.getLottoList().add(randomNumber);
            }
        }
    }


    private boolean getIsDuplicated(int randomNumber) {
        return lottoVO.getLottoList().contains(randomNumber);
    }

    private boolean getIsExcept(List<Integer> exceptList, int randomNumber) {
        if (exceptList == null || exceptList.isEmpty()) return false;
        return exceptList.contains(randomNumber);
    }

    private void sortLottoList() {
        lottoVO.getLottoList().sort(Comparator.naturalOrder());
    }

    private void setLottoResponse(DefaultLottoResponse lottoResponse,
                                  List<Integer> exceptList,
                                  List<Integer> needsList) {
        setLotto(exceptList, needsList);
        lottoResponse.setFirstNumber(lottoVO.getLottoList().get(0));
        lottoResponse.setSecondNumber(lottoVO.getLottoList().get(1));
        lottoResponse.setThirdNumber(lottoVO.getLottoList().get(2));
        lottoResponse.setFourthNumber(lottoVO.getLottoList().get(3));
        lottoResponse.setFifthNumber(lottoVO.getLottoList().get(4));
        lottoResponse.setSixthNumber(lottoVO.getLottoList().get(5));
    }

    private void setLottoListResponse(int price,
                                      List<Integer> exceptList,
                                      List<Integer> needsList,
                                      RandomLottoListResponse lottoListGetResponse) {
        List<DefaultLottoResponse> lottoDetails = new ArrayList<>();
        for (int i = 0; i < getLottoCount(price); i++) {
            DefaultLottoResponse lotto = new DefaultLottoResponse();
            setLottoResponse(lotto, exceptList, needsList);
            lottoDetails.add(lotto);
        }
        lottoListGetResponse.setLottoList(lottoDetails);
    }

    private void setWinningNumbersResponse(LottoWinningHistoryEntity entity,
                                           LottoWinningNumbersResponse response) {
        response.setFirstNumber(entity.getFirstNumber());
        response.setSecondNumber(entity.getSecondNumber());
        response.setThirdNumber(entity.getThirdNumber());
        response.setFourthNumber(entity.getFourthNumber());
        response.setFifthNumber(entity.getFifthNumber());
        response.setSixthNumber(entity.getSixthNumber());
        response.setBonusNumber(entity.getBonusNumber());
    }

}

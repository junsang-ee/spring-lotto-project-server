package com.lotto.web.service;

import com.lotto.web.model.dto.request.LottoListRequest;
import com.lotto.web.model.dto.response.DefaultLottoResponse;
import com.lotto.web.model.dto.response.DefaultLottoListResponse;
import com.lotto.web.model.dto.response.LottoWinningNumbersResponse;
import com.lotto.web.model.entity.LottoHistoryEntity;
import com.lotto.web.model.vo.LottoVO;
import com.lotto.web.repository.LottoHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static com.lotto.web.utils.LottoUtil.*;

@RequiredArgsConstructor
@Service
public class LottoServiceImpl implements LottoService{

    private final LottoVO lottoVO;

    private final LottoHistoryRepository lottoHistoryRepository;

    @Override
    public DefaultLottoListResponse getRandomList(int price,
                                                  List<Integer> exceptList,
                                                  List<Integer> needsList) {
        if (!getIsCorrectPrice(price)) return null;
        DefaultLottoListResponse result = new DefaultLottoListResponse();
        setLottoListResponse(price, exceptList, needsList, result);
        return result;
    }

    @Override
    public void saveWinningNumbers() {

    }

    @Override
    public LottoWinningNumbersResponse getWinningNumbersByRound(int round) {
        LottoHistoryEntity entity = lottoHistoryRepository.findByRound(round);
        LottoWinningNumbersResponse response = new LottoWinningNumbersResponse();
        setWinningNumbersResponse(entity, response);
        return response;
    }

    @Override
    public LottoWinningNumbersResponse getWinningNumbersByDrawDate(Date drawDate) {
        LottoHistoryEntity entity = lottoHistoryRepository.findByDrawDate(drawDate);
        LottoWinningNumbersResponse response = new LottoWinningNumbersResponse();
        setWinningNumbersResponse(entity, response);
        return response;
    }

    @Override
    public List<LottoHistoryEntity> getAllWinningNumbers() {
        return lottoHistoryRepository.findAll();
    }

    private void setLotto(List<Integer> excludedList, List<Integer> needsList) {
        if (!lottoVO.getLottoList().isEmpty()) {
            lottoVO.resetLottoNumbers();
        }
        if (needsList != null && !needsList.isEmpty()) {
            lottoVO.setNeedsNumbers(needsList);
        }
        while (lottoVO.getLottoList().size() < 6) {
            addLottoNumber(excludedList);
        }
        sortLottoList();
    }

    private void addLottoNumber(List<Integer> excludedList) {
        if (lottoVO.getLottoList() != null) {
            int randomNumber = getRandomNumber();
            if (!getIsDuplicated(randomNumber) && !getIsExcluded(excludedList, randomNumber)) {
                lottoVO.getLottoList().add(randomNumber);
            }
        }
    }


    private boolean getIsDuplicated(int randomNumber) {
        return lottoVO.getLottoList().contains(randomNumber);
    }

    private boolean getIsExcluded(List<Integer> excludedList, int randomNumber) {
        if (excludedList == null || excludedList.isEmpty()) return false;
        return excludedList.contains(randomNumber);
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
                                      DefaultLottoListResponse lottoListGetResponse) {
        List<DefaultLottoResponse> lottoDetails = new ArrayList<>();
        for (int i = 0; i < getLottoCount(price); i++) {
            DefaultLottoResponse lotto = new DefaultLottoResponse();
            setLottoResponse(lotto, exceptList, needsList);
            lottoDetails.add(lotto);
        }
        lottoListGetResponse.setLottoList(lottoDetails);
    }

    private void setWinningNumbersResponse(LottoHistoryEntity entity,
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

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

import static com.lotto.web.utils.LottoUtil.getIsCorrectPrice;
import static com.lotto.web.utils.LottoUtil.getLottoCount;
import static com.lotto.web.utils.LottoUtil.getRandomNumber;


@RequiredArgsConstructor
@Service
public class LottoServiceImpl implements LottoService{

    private final LottoVO lottoVO;

    private final LottoHistoryRepository lottoHistoryRepository;

    @Override
    public DefaultLottoListResponse getRandomList(LottoListRequest request) {
        if (!getIsCorrectPrice(request.getPrice())) return null;
        DefaultLottoListResponse result = new DefaultLottoListResponse();
        setLottoListResponse(result, request);
        return result;
    }

    @Override
    public void saveWinningNumbers() {

    }

    @Override
    public LottoWinningNumbersResponse getWinningNumbersByRound(int round) {
        LottoHistoryEntity entity = lottoHistoryRepository.findByRound(round);

        return null;
    }

    @Override
    public LottoWinningNumbersResponse getWinningNumbersByDrawDate(Date drawDate) {
        return null;
    }

    private void setLotto(List<Integer> excludedList) {
        if (!lottoVO.getLottoList().isEmpty()) {
            resetLottoVo();
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

    private void resetLottoVo() {
        lottoVO.resetLottoNumbers();
    }

    private void sortLottoList() {
        lottoVO.getLottoList().sort(Comparator.naturalOrder());
    }

    private void setLottoResponse(DefaultLottoResponse lottoResponse, LottoListRequest request) {
        setLotto(request.getExceptList());
        lottoResponse.setFirstNumber(lottoVO.getLottoList().get(0));
        lottoResponse.setSecondNumber(lottoVO.getLottoList().get(1));
        lottoResponse.setThirdNumber(lottoVO.getLottoList().get(2));
        lottoResponse.setFourthNumber(lottoVO.getLottoList().get(3));
        lottoResponse.setFifthNumber(lottoVO.getLottoList().get(4));
        lottoResponse.setSixthNumber(lottoVO.getLottoList().get(5));
    }

    private void setLottoListResponse(DefaultLottoListResponse lottoListGetResponse,
                                      LottoListRequest request) {
        List<DefaultLottoResponse> lottoDetails = new ArrayList<>();
        for (int i = 0; i < getLottoCount(request.getPrice()); i++) {
            DefaultLottoResponse lotto = new DefaultLottoResponse();
            setLottoResponse(lotto, request);
            lottoDetails.add(lotto);
        }
        lottoListGetResponse.setLottoList(lottoDetails);
    }

    private void setWinningNumbersResponse(LottoWinningNumbersResponse response,
                                           LottoHistoryEntity entity) {
        DefaultLottoResponse defaultLottoResponse = new DefaultLottoResponse();

        response.setWinningNumber();
    }

    private void setDefaultLottoResponse(DefaultLottoResponse defaultLottoResponse) {

    }

}

package com.lotto.web.service;

import com.lotto.web.model.dto.request.LottoExceptListGetRequest;
import com.lotto.web.model.dto.response.LottoGetResponse;
import com.lotto.web.model.dto.response.LottoListGetResponse;
import com.lotto.web.model.vo.LottoVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.lotto.web.utils.LottoUtil.getIsCorrectPrice;
import static com.lotto.web.utils.LottoUtil.getLottoCount;
import static com.lotto.web.utils.LottoUtil.getRandomNumber;


@RequiredArgsConstructor
@Service
public class LottoServiceImpl implements LottoService{

    private final LottoVO lottoVO;

    @Override
    public LottoGetResponse get() {
        LottoGetResponse result = new LottoGetResponse();
        setLottoResponse(result);
        return result;
    }

    @Override
    public LottoListGetResponse list(int price) {
        if (!getIsCorrectPrice(price)) return null;
        LottoListGetResponse result = new LottoListGetResponse();
        setLottoListResponse(result, price);
        return result;
    }

    @Override
    public LottoListGetResponse excludedList(LottoExceptListGetRequest request) {
        return null;
    }

    private void setLotto() {
        if (!lottoVO.getLottoList().isEmpty()) {
            resetLottoVo();
        }
        while (lottoVO.getLottoList().size() < 6) {
            addLottoNumber();
        }
        sortLottoList();
    }

    private void addLottoNumber() {
        if (lottoVO.getLottoList() != null) {
            int randomNumber = getRandomNumber();
            if (!getIsDuplicated(randomNumber)) {
                lottoVO.getLottoList().add(randomNumber);
            }
        }
    }

    private boolean getIsDuplicated(int randomNumber) {
        return lottoVO.getLottoList().contains(randomNumber);
    }

    private void resetLottoVo() {
        lottoVO.resetLottoNumbers();
    }

    private void sortLottoList() {
        lottoVO.getLottoList().sort(Comparator.naturalOrder());
    }

    private void setLottoResponse(LottoGetResponse lottoResponse) {
        setLotto();
        lottoResponse.setFirstNumber(lottoVO.getLottoList().get(0));
        lottoResponse.setSecondNumber(lottoVO.getLottoList().get(1));
        lottoResponse.setThirdNumber(lottoVO.getLottoList().get(2));
        lottoResponse.setFourthNumber(lottoVO.getLottoList().get(3));
        lottoResponse.setFifthNumber(lottoVO.getLottoList().get(4));
        lottoResponse.setSixthNumber(lottoVO.getLottoList().get(5));
    }

    private void setLottoListResponse(LottoListGetResponse lottoListGetResponse,
                                      int price) {
        List<LottoGetResponse> lottoDetails = new ArrayList<>();
        for (int i = 0; i < getLottoCount(price); i++) {
            LottoGetResponse lotto = new LottoGetResponse();
            setLottoResponse(lotto);
            lottoDetails.add(lotto);
        }
        lottoListGetResponse.setLottoList(lottoDetails);
    }
}

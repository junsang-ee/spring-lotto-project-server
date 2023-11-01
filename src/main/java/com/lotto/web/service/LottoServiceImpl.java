package com.lotto.web.service;

import com.lotto.web.model.dto.request.LottoExceptListGetRequest;
import com.lotto.web.model.dto.response.LottoGetResponse;
import com.lotto.web.model.dto.response.LottoListGetResponse;
import com.lotto.web.model.vo.LottoVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import static com.lotto.web.utils.LottoUtil.getIsCorrectPrice;
@RequiredArgsConstructor
@Service
public class LottoServiceImpl implements LottoService{

    private final LottoVO lottoVO;

    @Override
    public LottoGetResponse get() {
        setLottoVo();
        return getLottoResponse();
    }

    @Override
    public LottoListGetResponse list(int price) {
        if (getIsCorrectPrice(price)) return null;
        return null;
    }

    @Override
    public LottoListGetResponse excludedList(LottoExceptListGetRequest request) {
        return null;
    }

    private void setLottoVo() {
        if (!lottoVO.getLottoList().isEmpty()) {
            resetLottoVo();
        }
        while (lottoVO.getLottoList().size() < 6) {
            addLottoNumber();
        }
    }

    private int getRandomNumber() {
        return (int) (Math.random() * 45 + 1);
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

    private LottoGetResponse getLottoResponse() {
        sortLottoList();
        LottoGetResponse result = new LottoGetResponse();
        result.setFirstNumber(lottoVO.getLottoList().get(0));
        result.setSecondNumber(lottoVO.getLottoList().get(1));
        result.setThirdNumber(lottoVO.getLottoList().get(2));
        result.setFourthNumber(lottoVO.getLottoList().get(3));
        result.setFifthNumber(lottoVO.getLottoList().get(4));
        result.setSixthNumber(lottoVO.getLottoList().get(5));
        return result;
    }

    private void sortLottoList() {
        lottoVO.getLottoList().sort(Comparator.naturalOrder());
    }
}

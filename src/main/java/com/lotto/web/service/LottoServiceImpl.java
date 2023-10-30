package com.lotto.web.service;

import com.lotto.web.model.dto.response.LottoResponse;
import com.lotto.web.model.vo.LottoVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@RequiredArgsConstructor
@Service
public class LottoServiceImpl implements LottoService{

    private final LottoVO lottoVO;

    @Override
    public LottoResponse get() {
        setLottoVo();
        return getLottoResponse();
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

    private LottoResponse getLottoResponse() {
        sortLottoList();
        LottoResponse result = new LottoResponse();
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

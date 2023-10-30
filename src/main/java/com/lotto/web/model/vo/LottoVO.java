package com.lotto.web.model.vo;

import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Component
@Scope("prototype")
public class LottoVO {
    private List<Integer> lottoList = new ArrayList<>();

    public void resetLottoNumbers() {
        lottoList = new ArrayList<>();
    }
}

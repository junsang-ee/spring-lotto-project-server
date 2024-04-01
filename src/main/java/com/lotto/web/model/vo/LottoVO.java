package com.lotto.web.model.vo;

import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Getter
@Component
@Scope("singleton")
public class LottoVO {
    private List<Integer> lottoList = new ArrayList<>();

    public void resetLottoNumbers() {
        lottoList = new ArrayList<>();
    }

    public void setNeedsNumbers(List<Integer> needsNumbers) {
        lottoList.addAll(needsNumbers);
    }

    public void addNumber(int number) {
        lottoList.add(number);
    }


    public boolean getIsDuplicated(int number) {
        return lottoList.contains(number);
    }

    public boolean getIsEmpty() {
        return lottoList.isEmpty();
    }

    public boolean getIsNullable() {
        return lottoList == null;
    }

    public void sort() {
        lottoList.sort(Comparator.naturalOrder());
    }
}

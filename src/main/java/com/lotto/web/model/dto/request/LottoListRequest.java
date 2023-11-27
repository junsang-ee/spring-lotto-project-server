package com.lotto.web.model.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class LottoListRequest {
    private int price;
    private List<Integer> exceptList;
    private List<Integer> needsList;
}

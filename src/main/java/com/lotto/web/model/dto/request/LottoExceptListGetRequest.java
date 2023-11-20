package com.lotto.web.model.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class LottoExceptListGetRequest {
    private int price;
    private List<Integer> exceptList;
}

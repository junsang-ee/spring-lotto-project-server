package com.lotto.web.model.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;
@Getter
@RequiredArgsConstructor(access = PRIVATE)
public class RandomLottoListResponse {
    private final List<DefaultLottoResponse> lottoList;

    public static RandomLottoListResponse of(List<DefaultLottoResponse> lottos) {
        return new RandomLottoListResponse(lottos);
    }
    
}

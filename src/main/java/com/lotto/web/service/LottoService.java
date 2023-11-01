package com.lotto.web.service;

import com.lotto.web.model.dto.request.LottoExceptListGetRequest;
import com.lotto.web.model.dto.response.LottoGetResponse;
import com.lotto.web.model.dto.response.LottoListGetResponse;

public interface LottoService {
    LottoGetResponse get();

    LottoListGetResponse list(int price);

    LottoListGetResponse excludedList(LottoExceptListGetRequest request);
}

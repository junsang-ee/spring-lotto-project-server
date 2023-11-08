package com.lotto.web.service;

import com.lotto.web.model.dto.request.LottoListGetRequest;
import com.lotto.web.model.dto.response.LottoListGetResponse;

public interface LottoService {

    LottoListGetResponse list(LottoListGetRequest request);
}

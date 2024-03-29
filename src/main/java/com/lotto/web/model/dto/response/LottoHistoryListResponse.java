package com.lotto.web.model.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class LottoHistoryListResponse {
    private List<LottoHistoryDefaultResponse> lottoHistories;
}

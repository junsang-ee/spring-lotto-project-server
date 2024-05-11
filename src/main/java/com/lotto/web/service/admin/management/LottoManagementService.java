package com.lotto.web.service.admin.management;

import com.lotto.web.model.dto.response.ExtractionListResponse;
import com.lotto.web.model.entity.lotto.LottoWinningHistoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LottoManagementService {

    LottoWinningHistoryEntity saveWinningByRound(int round);

    Page<ExtractionListResponse> getExtractionsByUser(String userId, Pageable pageable);


}

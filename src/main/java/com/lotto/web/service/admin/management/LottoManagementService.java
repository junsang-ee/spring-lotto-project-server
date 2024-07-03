package com.lotto.web.service.admin.management;

import com.lotto.web.model.dto.response.ExtractionDrawResultResponse;
import com.lotto.web.model.dto.response.ExtractionListResponse;
import com.lotto.web.model.entity.lotto.LottoWinningHistoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LottoManagementService {

    LottoWinningHistoryEntity saveWinningByRound(int round);

    List<LottoWinningHistoryEntity> saveRecentWinnings(int recentNumber);
    Page<ExtractionListResponse> getExtractionsByUser(String userId, Pageable pageable);

    ExtractionDrawResultResponse updateExtractionWinningStatus(Long extractionId);


}

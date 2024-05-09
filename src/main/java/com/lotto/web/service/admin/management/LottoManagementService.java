package com.lotto.web.service.admin.management;

import com.lotto.web.model.entity.lotto.LottoWinningHistoryEntity;

public interface LottoManagementService {

    LottoWinningHistoryEntity saveWinningByRound(int round);
}

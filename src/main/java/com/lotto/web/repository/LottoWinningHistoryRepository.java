package com.lotto.web.repository;

import com.lotto.web.model.entity.lotto.LottoWinningHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LottoWinningHistoryRepository extends JpaRepository<LottoWinningHistoryEntity, Long> {

    LottoWinningHistoryEntity findByRound(int round);
}

package com.lotto.web.repository;

import com.lotto.web.model.entity.lotto.LottoWinningHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface LottoHistoryRepository extends JpaRepository<LottoWinningHistoryEntity, String> {
    LottoWinningHistoryEntity findByRound(int round);

    LottoWinningHistoryEntity findByDrawDate(Date drawDate);
}

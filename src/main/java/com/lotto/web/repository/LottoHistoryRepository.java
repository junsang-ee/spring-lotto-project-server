package com.lotto.web.repository;

import com.lotto.web.model.entity.LottoHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface LottoHistoryRepository extends JpaRepository<LottoHistoryEntity, String> {
    LottoHistoryEntity findByRound(int round);

    LottoHistoryEntity findByDrawDate(Date drawDate);
}

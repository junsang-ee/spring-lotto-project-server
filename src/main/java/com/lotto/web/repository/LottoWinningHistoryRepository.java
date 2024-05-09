package com.lotto.web.repository;

import com.lotto.web.model.entity.lotto.LottoWinningHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LottoWinningHistoryRepository extends JpaRepository<LottoWinningHistoryEntity, Long> {

    Optional<LottoWinningHistoryEntity> findByRound(int round);
}

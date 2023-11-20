package com.lotto.web.repository;

import com.lotto.web.model.entity.LottoHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LottoHistoryRepository extends JpaRepository<LottoHistoryEntity, String> {
}

package com.lotto.web.repository;

import com.lotto.web.model.entity.lotto.ExtractionHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtractionHistoryRepository extends JpaRepository<ExtractionHistoryEntity, Long> {
}

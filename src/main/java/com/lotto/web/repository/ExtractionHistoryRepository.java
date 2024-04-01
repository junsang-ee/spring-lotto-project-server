package com.lotto.web.repository;

import com.lotto.web.model.entity.UserEntity;
import com.lotto.web.model.entity.lotto.ExtractionHistoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtractionHistoryRepository extends JpaRepository<ExtractionHistoryEntity, Long> {

    Page<ExtractionHistoryEntity> findAllByCreatedBy(@Param("user") UserEntity user,
                                                     Pageable pageable);
}

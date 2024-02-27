package com.lotto.web.repository;

import com.lotto.web.model.dto.response.ExtractionDetailResponse;
import com.lotto.web.model.entity.UserEntity;
import com.lotto.web.model.entity.lotto.ExtractionHistoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtractionHistoryRepository extends JpaRepository<ExtractionHistoryEntity, Long> {

    @Query(value = "SELECT " +
                        "new com.lotto.web.model.dto.response.ExtractionDetailResponse(" +
                            "e.firstNumber, e.secondNumber, e.thirdNumber, " +
                            "e.fourthNumber, e.fifthNumber, e.sixthNumber, " +
                            "w.firstStatus, w.secondStatus, w.thirdStatus, " +
                            "w.fourthStatus, w.fifthStatus, w.sixthStatus, " +
                            "w.overallStatus as winningResult, e.createdAt" +
                        ") " +
                     "FROM extraction_history e " +
               "INNER JOIN winning_status w on w.id = e.winningStatus " +
                    "WHERE e.createdBy = :user")
    Page<ExtractionDetailResponse> getAllExtraction(@Param("user") UserEntity user,
                                                    Pageable pageable);
}

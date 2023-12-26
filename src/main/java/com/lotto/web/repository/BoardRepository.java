package com.lotto.web.repository;

import com.lotto.web.constants.BoardAccessType;
import com.lotto.web.constants.BoardActivationStatus;
import com.lotto.web.model.dto.response.BoardDetailResponse;
import com.lotto.web.model.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, String> {

    @Query(value = "SELECT " +
                        "new com.lotto.web.model.dto.response.BoardDetailResponse(b.id, b.name) " +
                     "FROM board b " +
                    "WHERE b.status = :status")
    List<BoardDetailResponse> getAllByStatus(@Param("status") BoardActivationStatus status);
    @Query(value = "SELECT " +
                        "new com.lotto.web.model.dto.response.BoardDetailResponse(b.id, b.name) " +
                    "FROM board b " +
                   "WHERE b.accessType = :accessType " +
                     "AND b.status = :status")
    List<BoardDetailResponse> getAllByBoardByAccessTypeAndStatus(@Param("accessType") BoardAccessType accessType,
                                                                 @Param("status")BoardActivationStatus status);

}

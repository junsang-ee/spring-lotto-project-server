package com.lotto.web.repository;

import com.lotto.web.constants.BoardAccessType;
import com.lotto.web.constants.BoardActivationStatus;
import com.lotto.web.model.dto.response.BoardListEntryResponse;
import com.lotto.web.model.dto.response.admin.BoardManageListResponse;
import com.lotto.web.model.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, String> {

    @Query(value = "SELECT " +
                        "new com.lotto.web.model.dto.response.BoardListEntryResponse(b.id, b.name) " +
                     "FROM board b " +
                    "WHERE b.status = :status")
    List<BoardListEntryResponse> getAllByStatus(@Param("status") BoardActivationStatus status);

    @Query(value = "SELECT " +
                        "new com.lotto.web.model.dto.response.admin.BoardManageListResponse(" +
                            "b.id, " +
                            "b.name, " +
                            "b.status, " +
                            "b.accessType, " +
                            "p.enabledCount as enabledPostCount, " +
                            "p.disabledCount as disabledPostCount" +
                        ") " +
                     "FROM board b " +
               "INNER JOIN post_count p on p.id = b.postCount")
    Page<BoardManageListResponse> getAllBoard(Pageable pageable);


    @Query(value = "SELECT " +
                        "new com.lotto.web.model.dto.response.BoardListEntryResponse(b.id, b.name) " +
                    "FROM board b " +
                   "WHERE b.accessType = :accessType " +
                     "AND b.status = :status")
    List<BoardListEntryResponse> getAllByBoardByAccessTypeAndStatus(@Param("accessType") BoardAccessType accessType,
                                                                    @Param("status")BoardActivationStatus status);

}

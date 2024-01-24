package com.lotto.web.repository;

import com.lotto.web.config.dsl.QueryDslPredicateExtendedExecutor;
import com.lotto.web.constants.PostActivationStatus;
import com.lotto.web.model.dto.response.PostListEntryResponse;
import com.lotto.web.model.entity.BoardEntity;
import com.lotto.web.model.entity.PostEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, String>,
        QueryDslPredicateExtendedExecutor<PostEntity> {

    @Query(value = "SELECT " +
                      "new com.lotto.web.model.dto.response.PostListEntryResponse(" +
                                "p.title, " +
                                "CASE " +
                                    "WHEN p.disclosureType = 'PUBLIC' then '공개' " +
                                    "ELSE '비공개' " +
                                "END as disclosureType, " +
                                "u.email, " +
                                "TO_CHAR(p.createdAt, 'YYYY-MM-DD') as createdAt, " +
                                "p.viewCount" +
                            ") " +
                     "FROM post p " +
               "INNER JOIN user u on u.id = p.createdBy " +
                    "WHERE p.status = :status " +
                      "AND p.parentBoard = :parentBoard")
    Page<PostListEntryResponse> findAllByParentBoardAndStatus(@Param("status") PostActivationStatus status,
                                                              @Param("parentBoard") BoardEntity parentBoard,
                                                              Pageable pageable);

}

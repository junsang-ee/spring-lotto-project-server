package com.lotto.web.repository;

import com.lotto.web.config.dsl.QueryDslPredicateExtendedExecutor;
import com.lotto.web.constants.PostActivationStatus;
import com.lotto.web.model.entity.PostEntity;
import com.lotto.web.model.querydsl.PostListQueryResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, String>,
        QueryDslPredicateExtendedExecutor<PostEntity> {

    @Query(value = "SELECT p.title, p.disclosure_type, u.email, p.created_at, p.view_count " +
                     "FROM post p " +
                "LEFT JOIN user u on p.created_by = u.id " +
                    "WHERE p.status=:status " +
                      "AND p.parent_board=:boardId",
            nativeQuery = true)
    Page<PostListQueryResult> findAllByParentBoardAndStatus(@Param("status") PostActivationStatus status,
                                                                  @Param("boardId") String boardId,
                                                                  Pageable pageable);

}

package com.lotto.web.repository;

import com.lotto.web.config.dsl.QueryDslPredicateExtendedExecutor;
import com.lotto.web.constants.PostActivationStatus;
import com.lotto.web.model.dto.response.PostListEntryResponse;
import com.lotto.web.model.dto.response.admin.PostManageDetailResponse;
import com.lotto.web.model.entity.BoardEntity;
import com.lotto.web.model.entity.PostEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, String> {
//        QueryDslPredicateExtendedExecutor<PostEntity> {

    @Query(value = "SELECT " +
                      "new com.lotto.web.model.dto.response.PostListEntryResponse(" +
                                "p.id, " +
                                "p.title, " +
                                "p.disclosureType, " +
                                "u.email, " +
                                "p.createdAt, " +
                                "p.viewCount" +
                            ") " +
                     "FROM post p " +
               "INNER JOIN user u on u.id = p.createdBy " +
                    "WHERE p.status = :status " +
                      "AND p.parentBoard = :parentBoard")
    Page<PostListEntryResponse> findAllByParentBoardAndStatus(@Param("status") PostActivationStatus status,
                                                              @Param("parentBoard") BoardEntity parentBoard,
                                                              Pageable pageable);

    @Query("SELECT " +
                "new com.lotto.web.model.dto.response.admin.PostManageDetailResponse(" +
                    "p.id, " +
                    "p.title, " +
                    "u.email as writer, " +
                    "p.disclosureType, " +
                    "p.status, " +
                    "rc.enabledCount as enabledReplyCount, " +
                    "rc.disabledCount as disabledReplyCount, " +
                    "rc.removedCount as removedReplyCount, " +
                    "p.createdAt" +
                ") " +
             "FROM post p " +
       "INNER JOIN user u on u.id = p.createdBy " +
       "INNER JOIN reply_count rc on rc.id = p.replyCount " +
            "WHERE p.parentBoard = :parentBoard")
    Page<PostManageDetailResponse> getAllPost(@Param("parentBoard") BoardEntity parentBoard,
                                              Pageable pageable);

}

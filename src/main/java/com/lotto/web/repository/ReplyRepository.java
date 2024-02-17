package com.lotto.web.repository;

import com.lotto.web.constants.PostActivationStatus;
import com.lotto.web.model.dto.response.ReplyDetailResponse;
import com.lotto.web.model.entity.PostEntity;
import com.lotto.web.model.entity.ReplyEntity;
import com.lotto.web.model.entity.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyRepository extends JpaRepository<ReplyEntity, String> {

    @Query(value = "SELECT " +
                        "new com.lotto.web.model.dto.response.ReplyDetailResponse(" +
                            "r.id, " +
                            "u.email, " +
                            "r.content, " +
                            "CASE " +
                                "WHEN r.createdBy = :user THEN true " +
                                "ELSE false " +
                            "END AS mine) " +
                     "FROM reply r " +
               "INNER JOIN user u on u.id = r.createdBy " +
                    "WHERE r.parentPost = :parentPost " +
                      "AND r.status = :status")
    List<ReplyDetailResponse> findAllByParentPostAndStatus(@Param("user") UserEntity user,
                                                           @Param("status") PostActivationStatus status,
                                                           @Param("parentPost") PostEntity parentPost,
                                                           Pageable pageable);
}

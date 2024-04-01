package com.lotto.web.repository;

import com.lotto.web.constants.PostActivationStatus;
import com.lotto.web.model.entity.PostEntity;
import com.lotto.web.model.entity.ReplyEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyRepository extends JpaRepository<ReplyEntity, String> {

    List<ReplyEntity> findAllByParentPostAndStatus(@Param("parentPost") PostEntity parentPost,
                                                   @Param("status") PostActivationStatus status,
                                                   Pageable pageable);
}

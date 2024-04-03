package com.lotto.web.repository;

import com.lotto.web.constants.PostActivationStatus;
import com.lotto.web.model.dto.response.admin.UserPostListResponse;
import com.lotto.web.model.entity.BoardEntity;
import com.lotto.web.model.entity.PostEntity;

import com.lotto.web.model.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, String> {

    Page<PostEntity> findAllByParentBoardAndStatus(@Param("parentBoard") BoardEntity parentBoard,
                                                   @Param("status") PostActivationStatus status,
                                                   Pageable pageable);

    Page<PostEntity> findAllByParentBoard(@Param("parentBoard") BoardEntity parentBoard,
                                          Pageable pageable);

    Page<PostEntity> findAllByCreatedBy(@Param("user") UserEntity createdBy,
                                        Pageable pageable);


}

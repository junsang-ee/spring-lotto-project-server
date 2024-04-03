package com.lotto.web.repository;

import com.lotto.web.constants.BoardAccessType;
import com.lotto.web.constants.BoardActivationStatus;
import com.lotto.web.model.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, String> {

    List<BoardEntity> findAllByStatusAndAccessType(@Param("status") BoardActivationStatus status,
                                                   @Param("accessType") BoardAccessType accessType);



}

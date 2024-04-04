package com.lotto.web.repository;


import com.lotto.web.constants.UserRole;
import com.lotto.web.constants.UserStatus;
import com.lotto.web.model.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findByEmail(String email);

    UserEntity findByRole(UserRole role);

    Optional<UserEntity> findByIdOrEmail(String userId, String email);

    List<UserEntity> findAllByStatusAndRole(UserStatus status, UserRole role);

    Page<UserEntity> findByIdNot(String id, Pageable pageable);


}

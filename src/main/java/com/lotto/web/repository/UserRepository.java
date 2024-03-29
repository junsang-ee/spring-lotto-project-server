package com.lotto.web.repository;


import com.lotto.web.constants.UserRole;
import com.lotto.web.constants.UserStatus;
import com.lotto.web.model.dto.response.admin.UserManageDetailResponse;
import com.lotto.web.model.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByIdOrEmail(String userId, String email);

    List<UserEntity> findAllByStatusAndRole(UserStatus status, UserRole role);

    @Query(value = "SELECT " +
                        "new com.lotto.web.model.dto.response.admin.UserManageDetailResponse(" +
                            "u.id, " +
                            "u.email, " +
                            "u.status, " +
                            "u.dailyAvailableCount, " +
                            "count(p) as postCount, " +
                            "u.createdAt" +
                        ") " +
                     "FROM user u " +
                "LEFT JOIN post p on p.createdBy = u.id " +
                    "WHERE u != :admin " +
                 "GROUP BY u.id")
    Page<UserManageDetailResponse> getAllUser(@Param("admin") UserEntity admin,
                                              Pageable pageable);
}

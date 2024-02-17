package com.lotto.web.repository;

import com.lotto.web.model.entity.admin.AdminSettingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminSettingRepository extends JpaRepository<AdminSettingEntity, Long> {
}

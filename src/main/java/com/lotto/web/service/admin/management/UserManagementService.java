package com.lotto.web.service.admin.management;

import com.lotto.web.constants.UserStatus;
import com.lotto.web.model.dto.response.admin.UserManageDetailResponse;
import com.lotto.web.model.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserManagementService {

    UserEntity get(String userId);

    Page<UserManageDetailResponse> list(Pageable pageable);

    boolean updateStatus(String userId, UserStatus status);
}

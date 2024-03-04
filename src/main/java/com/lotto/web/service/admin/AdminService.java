package com.lotto.web.service.admin;

import com.lotto.web.constants.PostActivationStatus;
import com.lotto.web.constants.UserStatus;
import com.lotto.web.model.dto.request.SettingUpdateRequest;
import com.lotto.web.model.dto.response.admin.UserManageDetailResponse;
import com.lotto.web.model.entity.BoardEntity;
import com.lotto.web.model.entity.PostEntity;
import com.lotto.web.model.entity.UserEntity;

import com.lotto.web.model.entity.lotto.LottoWinningHistoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminService {

    void createAdminSetting();
    void createAdminAccount();

    void updateLottoAutomationSetting(SettingUpdateRequest toggle);

    Page<UserManageDetailResponse> getUserList(Pageable pageable);

    UserEntity getUserDetail(String userId);

    void updateUserStatus(String userId, UserStatus status);

    UserEntity getAdmin();

    LottoWinningHistoryEntity saveWinningByRound(String round);
}

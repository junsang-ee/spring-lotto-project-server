package com.lotto.web.service.admin;

import com.lotto.web.model.dto.request.SettingUpdateRequest;
import com.lotto.web.model.entity.UserEntity;

import com.lotto.web.model.entity.lotto.LottoWinningHistoryEntity;

public interface AdminService {

    void createAdminSetting();
    UserEntity createAdminAccount();

    void updateLottoAutomationSetting(SettingUpdateRequest toggle);

    UserEntity getAdmin();
}

package com.lotto.web.service.admin;

import com.lotto.web.model.dto.request.SettingUpdateRequest;

public interface SettingService {

    void load();
    void updateLottoAutomationSetting(SettingUpdateRequest toggle);
}

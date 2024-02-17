package com.lotto.web.service.admin;

import com.lotto.web.constants.SettingToggleType;
import com.lotto.web.model.dto.request.SettingUpdateRequest;
import com.lotto.web.model.entity.admin.AdminSettingEntity;
import com.lotto.web.repository.AdminSettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class SettingServiceImpl implements SettingService {

    private final AdminSettingRepository adminSettingRepository;

    @Override
    @Transactional
    public void load() {
        if (adminSettingRepository.findById(1L).isPresent()) return;
        AdminSettingEntity entity = new AdminSettingEntity();
        entity.setLottoAutoUpdateToggle(SettingToggleType.OFF);
        adminSettingRepository.save(entity);
    }

    @Override
    @Transactional
    public void updateLottoAutomationSetting(SettingUpdateRequest toggle) {
        AdminSettingEntity entity = new AdminSettingEntity();
        entity.setLottoAutoUpdateToggle(toggle.getType());
        adminSettingRepository.save(entity);
    }
}

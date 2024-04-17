package com.lotto.web.service.admin;

import com.lotto.web.constants.*;
import com.lotto.web.constants.messages.ErrorMessage;
import com.lotto.web.exception.custom.NotFoundException;
import com.lotto.web.model.dto.request.SettingUpdateRequest;
import com.lotto.web.model.entity.UserEntity;
import com.lotto.web.model.entity.admin.AdminSettingEntity;
import com.lotto.web.model.entity.lotto.LottoWinningHistoryEntity;
import com.lotto.web.repository.*;

import com.lotto.web.util.WebClientUtil;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

    private final AdminSettingRepository adminSettingRepository;

    private final LottoWinningHistoryRepository lottoWinningHistoryRepository;

    private final WebClientUtil webClientUtil;

    @Value("${junsang.admin.email}")
    private String adminEmail;

    @Value("${junsang.admin.password}")
    private String adminPassword;

    @Override
    @Transactional
    public UserEntity createAdminAccount() {
        Optional<UserEntity> admin = userRepository.findByEmail(adminEmail);
        return admin.orElseGet(
                () -> userRepository.save(
                        UserEntity.of(
                                adminEmail,
                                adminPassword,
                                UserRole.ADMIN
                        )
                )
        );
    }

    @Override
    @Transactional
    public void createAdminSetting() {
        if (adminSettingRepository.findById(1L).isPresent()) return;
        adminSettingRepository.save(AdminSettingEntity.of(SettingToggleType.OFF));
    }

    @Override
    @Transactional
    public void updateLottoAutomationSetting(SettingUpdateRequest toggle) {
        AdminSettingEntity settingEntity = adminSettingRepository.findById(1L).orElseThrow();
        settingEntity.update(toggle.getType());
    }

    @Override
    public UserEntity getAdmin() {
        return userRepository.findByEmail(adminEmail).orElseThrow(
                () -> new NotFoundException(ErrorMessage.USER_NOT_FOUND)
        );
    }

    @Override
    @Transactional
    public LottoWinningHistoryEntity saveWinningByRound(int round) {
        LottoWinningHistoryEntity lottoWinningHistoryEntity = webClientUtil.get(round);
        return lottoWinningHistoryRepository.save(lottoWinningHistoryEntity);
    }

}

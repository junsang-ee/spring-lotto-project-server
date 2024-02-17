package com.lotto.web.config;

import com.lotto.web.service.UserService;
import com.lotto.web.service.admin.SettingService;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Component
public class ApplicationInitializationConfig implements ApplicationRunner {

    private final SettingService settingService;

    private final UserService userService;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        settingService.load();
        userService.initializeAdministratorAccount();
    }
}

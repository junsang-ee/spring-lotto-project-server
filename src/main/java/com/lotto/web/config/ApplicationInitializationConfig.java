package com.lotto.web.config;

import com.lotto.web.service.admin.AdminService;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Component
public class ApplicationInitializationConfig implements ApplicationRunner {

    private final AdminService adminService;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        adminService.createAdminSetting();
        adminService.createAdminAccount();
    }
}

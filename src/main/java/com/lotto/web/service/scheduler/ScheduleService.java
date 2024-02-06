package com.lotto.web.service.scheduler;

import com.lotto.web.model.entity.UserEntity;
import com.lotto.web.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Component
public class ScheduleService {

    private final UserService userService;

    @Scheduled(cron = "@midnight")
    public void resetLottoAvailableCount() {
        log.info("=== Start Scheduler resetting the number of times users can use the service ===");
        List<UserEntity> users = userService.getAllEnabledUser();
        for (UserEntity user : users) {
            user.setDailyAvailableCount(100);
        }
        userService.saveAll(users);
    }
}

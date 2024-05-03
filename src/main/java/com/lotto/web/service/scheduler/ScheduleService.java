package com.lotto.web.service.scheduler;

import com.lotto.web.constants.WinningStatus;
import com.lotto.web.model.entity.UserEntity;
import com.lotto.web.model.entity.lotto.ExtractionHistoryEntity;
import com.lotto.web.repository.ExtractionHistoryRepository;
import com.lotto.web.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Component
public class ScheduleService {

    private final UserService userService;

    private final ExtractionHistoryRepository extractionHistoryRepository;

    @Transactional
    @Scheduled(cron = "@midnight")
    public void resetLottoAvailableCount() {
        log.info("=== Start Scheduler resetting the number of times users can use the service ===");
        List<UserEntity> users = userService.getAllEnabledUser();
        for (UserEntity user : users) {
            user.updateAvailableCount(100);
        }
    }

    @Transactional
    @Scheduled(cron = "0 35 20 * * SAT")
    public void setExtractionsAsWaiting() {
        log.info("=== Begin the process of changing non-drawn(WinningStatus: PENDING) random numbers to draw standby values ===");
        List<ExtractionHistoryEntity> extractions =
                extractionHistoryRepository.findAllByWinningStatusOverallStatus(WinningStatus.PENDING);
        for (ExtractionHistoryEntity extraction : extractions) {
            extraction.getWinningStatus().updateAllAsWaiting();
        }
    }
}

package com.lotto.web.batch;

import com.lotto.web.constants.WinningResultType;
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
public class SchedulerTasks {

    private final UserService userService;

    private final ExtractionHistoryRepository extractionHistoryRepository;

    @Transactional
    @Scheduled(cron = "@midnight")
    public void resetLottoAvailableCount() {
        log.info("=== Start Scheduled resetting the number of times users can use the service ===");
        List<UserEntity> users = userService.getAllEnabledUser();
        for (UserEntity user : users) {
            user.updateAvailableCount(100);
        }
        log.info("=== End Scheduled resetting the number of times users can use the service ===");
    }

    @Transactional
    public void setExtractionsAsWaiting() {
        log.info("=== Start Batch to change the status of extracted random lotto numbers ===");
        List<ExtractionHistoryEntity> extractions =
                extractionHistoryRepository.findAllByWinningStatusWinningResult(WinningResultType.PENDING);
        for (ExtractionHistoryEntity extraction : extractions) {
            extraction.getWinningStatus().updateToWaiting();
        }
        log.info("=== End Batch to change the status of extracted random lotto numbers ===");
    }

}

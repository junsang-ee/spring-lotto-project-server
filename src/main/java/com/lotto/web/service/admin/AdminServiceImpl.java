package com.lotto.web.service.admin;

import com.lotto.web.constants.*;
import com.lotto.web.constants.messages.ErrorMessage;
import com.lotto.web.exception.custom.NotFoundException;
import com.lotto.web.model.dto.request.SettingUpdateRequest;
import com.lotto.web.model.entity.UserEntity;
import com.lotto.web.model.entity.admin.AdminSettingEntity;
import com.lotto.web.model.entity.lotto.LottoWinningHistoryEntity;
import com.lotto.web.repository.*;

import com.lotto.web.service.admin.crawler.CrawlerService;
import com.lotto.web.util.LottoUtil;
import lombok.RequiredArgsConstructor;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

    private final AdminSettingRepository adminSettingRepository;

    private final CrawlerService crawlerService;

    private final LottoWinningHistoryRepository lottoWinningHistoryRepository;
    
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
                ));
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
    public LottoWinningHistoryEntity saveWinningByRound(String round) {
        Document document = crawlerService.getLottoDocumentByRound(round);
        Element dateElement = document.select(LottoUtil.DIV_DATE).get(0);
        Element winningElement = document.select(LottoUtil.DIV_WINNINGS).get(0);
        Element bonusElement = document.select(LottoUtil.DIV_BONUS).get(0);
        List<Integer> winningList = getWinningList(winningElement);
        Date drawDate = getLottoDrawDate(dateElement);
        LottoWinningHistoryEntity winningEntity = LottoWinningHistoryEntity.of(
                winningList,
                Integer.parseInt(bonusElement.text()),
                Integer.parseInt(round),
                drawDate
        );
        return lottoWinningHistoryRepository.save(winningEntity);
    }

    private List<Integer> getWinningList(Element element) {
        String[] winningArr = element.text().split("\\s+");
        return Arrays.stream(winningArr).map(
                Integer::parseInt
        ).collect(Collectors.toList());
    }

    private Date getLottoDrawDate(Element element) {
        String dateStr = element.text().substring(
                element.text().indexOf("(")+1,
                element.text().indexOf(")")-1
        );
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
        Date result = null;
        try {
            result = formatter.parse(dateStr);
        } catch (ParseException ignored) {
        }
        return result;
    }

}

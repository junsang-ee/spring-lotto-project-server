package com.lotto.web.service.admin;

import com.lotto.web.constants.*;
import com.lotto.web.constants.messages.ErrorMessage;
import com.lotto.web.exception.custom.AuthException;
import com.lotto.web.exception.custom.NotFoundException;
import com.lotto.web.model.dto.request.SettingUpdateRequest;
import com.lotto.web.model.dto.response.admin.PostManageDetailResponse;
import com.lotto.web.model.dto.response.admin.UserManageDetailResponse;
import com.lotto.web.model.entity.BoardEntity;
import com.lotto.web.model.entity.PostEntity;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final AdminSettingRepository adminSettingRepository;

    private final BoardRepository boardRepository;

    private final PostRepository postRepository;

    private final CrawlerService crawlerService;

    private final LottoWinningHistoryRepository lottoWinningHistoryRepository;
    
    @Value("${junsang.admin.email}")
    private String adminEmail;

    @Value("${junsang.admin.password}")
    private String adminPassword;

    @Override
    @Transactional
    public void deletePost(String postId) {
        PostEntity post = getPostDetail(postId);
        postRepository.delete(post);
    }

    @Override
    @Transactional
    public void createAdminAccount() {
        if (userRepository.findByEmail(adminEmail).isPresent())
            return;
        UserEntity admin = new UserEntity();
        admin.setRole(UserRole.ADMIN);
        admin.setEmail(adminEmail);
        admin.setPassword(passwordEncoder.encode(adminPassword));
        userRepository.save(admin);
    }

    @Override
    @Transactional
    public void createAdminSetting() {
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

    @Override
    public Page<UserManageDetailResponse> getUserList(Pageable pageable) {
        Page<UserManageDetailResponse> list = userRepository.getAllUser(adminEmail, pageable);
        return new PageImpl<>(
                list.stream().collect(Collectors.toList()),
                list.getPageable(),
                list.getTotalElements()
        );
    }

    @Override
    public Page<PostManageDetailResponse> getPostList(String boardId, Pageable pageable) {
        BoardEntity parentBoard = getBoardDetail(boardId);
        Page<PostManageDetailResponse> list = postRepository.getAllPost(parentBoard, pageable);
        return new PageImpl<>(
                list.stream().collect(Collectors.toList()),
                list.getPageable(),
                list.getTotalElements()
        );
    }

    @Override
    public UserEntity getUserDetail(String userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException(ErrorMessage.USER_NOT_FOUND)
        );
    }

    @Override
    public PostEntity getPostDetail(String postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new NotFoundException(ErrorMessage.POST_NOT_FOUND)
        );
    }

    @Override
    @Transactional
    public void updateUserStatus(String userId, UserStatus status) {
        UserEntity user = getUserDetail(userId);
        if (status == user.getStatus()) {
            switch (user.getStatus()) {
                case DISABLED:
                    throw new AuthException(ErrorMessage.AUTH_DISABLED);
                case RETIRED:
                    throw new AuthException(ErrorMessage.AUTH_RETIRED);
            }
        }
        user.setStatus(status);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updatePostStatus(String postId, PostActivationStatus status) {
        PostEntity post = getPostDetail(postId);
        post.setStatus(status);
        postRepository.save(post);
    }

    @Override
    public UserEntity getAdmin() {
        return userRepository.findByEmail(adminEmail).orElseThrow(
                () -> new NotFoundException(ErrorMessage.USER_NOT_FOUND)
        );
    }

    @Override
    public BoardEntity getBoardDetail(String boardId) {
        return boardRepository.findById(boardId).orElseThrow(
                () -> new NotFoundException(ErrorMessage.BOARD_NOT_FOUND)
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
        LottoWinningHistoryEntity winningEntity = new LottoWinningHistoryEntity();
        setWinningHistory(
                winningEntity,
                winningList,
                drawDate,
                Integer.parseInt(bonusElement.text()),
                Integer.parseInt(round)
        );
        return lottoWinningHistoryRepository.save(winningEntity);
    }

    private void setWinningHistory(LottoWinningHistoryEntity entity,
                                   List<Integer> winningList,
                                   Date drawDate,
                                   int bonus,
                                   int round) {
        entity.setFirstNumber(winningList.get(0));
        entity.setSecondNumber(winningList.get(1));
        entity.setThirdNumber(winningList.get(2));
        entity.setFourthNumber(winningList.get(3));
        entity.setFifthNumber(winningList.get(4));
        entity.setSixthNumber(winningList.get(5));
        entity.setDrawDate(drawDate);
        entity.setBonusNumber(bonus);
        entity.setRound(round);
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

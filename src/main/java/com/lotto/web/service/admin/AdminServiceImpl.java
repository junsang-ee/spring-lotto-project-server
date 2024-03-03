package com.lotto.web.service.admin;

import com.lotto.web.constants.*;
import com.lotto.web.constants.messages.ErrorMessage;
import com.lotto.web.exception.custom.AuthException;
import com.lotto.web.exception.custom.InvalidStateException;
import com.lotto.web.exception.custom.NotFoundException;
import com.lotto.web.model.dto.request.BoardSaveRequest;
import com.lotto.web.model.dto.request.SettingUpdateRequest;
import com.lotto.web.model.dto.response.admin.BoardManageDetailResponse;
import com.lotto.web.model.dto.response.admin.PostManageDetailResponse;
import com.lotto.web.model.dto.response.admin.UserManageDetailResponse;
import com.lotto.web.model.entity.BoardEntity;
import com.lotto.web.model.entity.PostEntity;
import com.lotto.web.model.entity.UserEntity;
import com.lotto.web.model.entity.admin.AdminSettingEntity;
import com.lotto.web.repository.AdminSettingRepository;
import com.lotto.web.repository.BoardRepository;
import com.lotto.web.repository.PostRepository;
import com.lotto.web.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final AdminSettingRepository adminSettingRepository;

    private final BoardRepository boardRepository;

    private final PostRepository postRepository;
    @Value("${junsang.admin.email}")
    private String adminEmail;

    @Value("${junsang.admin.password}")
    private String adminPassword;

    @Override
    @Transactional
    public BoardEntity saveBoard(BoardSaveRequest request) {
        BoardEntity board = new BoardEntity();
        setBoardEntity(getAdmin(), board, request);
        return boardRepository.save(board);
    }

    @Override
    @Transactional
    public boolean deleteBoard(String boardId) {
        BoardEntity board = boardRepository.findById(boardId).orElseThrow(
                () -> new NotFoundException(ErrorMessage.BOARD_NOT_FOUND)
        );
        if (board.getStatus() == BoardActivationStatus.REMOVED) {
            throw new InvalidStateException(ErrorMessage.BOARD_REMOVED);
        }
        board.setStatus(BoardActivationStatus.REMOVED);
        boardRepository.save(board);
        return false;
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
    public Page<BoardManageDetailResponse> getBoardList(Pageable pageable) {
        Page<BoardManageDetailResponse> list = boardRepository.getAllBoard(pageable);
        return new PageImpl<> (
                list.stream().collect(Collectors.toList()),
                list.getPageable(),
                list.getTotalElements());
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
    public void updateBoardStatus(String boardId, BoardActivationStatus status) {
        BoardEntity board = getBoardDetail(boardId);
        if (status == board.getStatus()) {
            if (board.getStatus() == BoardActivationStatus.REMOVED)
                throw new InvalidStateException(ErrorMessage.BOARD_REMOVED);
            else if (board.getStatus() == BoardActivationStatus.NORMAL)
                throw new InvalidStateException(ErrorMessage.BOARD_ENABLED);
        }
        board.setStatus(status);
        boardRepository.save(board);
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

    private void setBoardEntity(UserEntity admin,
                                BoardEntity entity,
                                BoardSaveRequest request) {
        entity.setCreatedBy(admin);
        entity.setName(request.getName());
        entity.setAccessType(request.getAccessType());
    }
}

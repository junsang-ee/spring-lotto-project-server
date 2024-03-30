package com.lotto.web.service;

import com.lotto.web.constants.UserRole;
import com.lotto.web.constants.UserStatus;
import com.lotto.web.constants.messages.ErrorMessage;
import com.lotto.web.exception.custom.AuthException;
import com.lotto.web.exception.custom.DuplicatedException;
import com.lotto.web.exception.custom.InvalidStateException;
import com.lotto.web.exception.custom.NotFoundException;

import com.lotto.web.model.dto.request.SignupRequest;
import com.lotto.web.model.dto.response.RandomLottoListResponse;
import com.lotto.web.model.dto.response.UserDetailResponse;
import com.lotto.web.model.entity.UserEntity;

import com.lotto.web.model.entity.lotto.ExtractionHistoryEntity;
import com.lotto.web.repository.ExtractionHistoryRepository;
import com.lotto.web.repository.UserRepository;
import com.lotto.web.util.EncryptUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ExtractionHistoryRepository extractionHistoryRepository;

    @Override
    public Optional<UserEntity> get(String userId) {
        return userRepository.findById(userId);
    }

    @Override
    public UserEntity getUser(String userId) {
        UserEntity user = get(userId).orElseThrow(
                () -> new NotFoundException(ErrorMessage.USER_NOT_FOUND)
        );
        if (user.getStatus() == UserStatus.DISABLED)
            throw new AuthException(ErrorMessage.AUTH_DISABLED);
        if (user.getStatus() == UserStatus.RETIRED)
            throw new AuthException(ErrorMessage.AUTH_RETIRED);
        return user;
    }

    @Override
    public UserEntity getUserForAdmin(String userId) {
        return get(userId).orElseThrow(
                () -> new NotFoundException(ErrorMessage.USER_NOT_FOUND)
        );
    }

    @Override
    public UserEntity getUserByEmailForAdmin(String email) {
        return getByEmail(email).orElseThrow(
                () -> new NotFoundException(ErrorMessage.USER_NOT_FOUND)
        );
    }

    @Override
    public UserDetailResponse getDetail(String userId) {
        UserEntity user = getUser(userId);
        return UserDetailResponse.of(user);
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        UserEntity user = getByEmail(email).orElseThrow(
                () -> new NotFoundException(ErrorMessage.USER_NOT_FOUND)
        );
        if (user.getStatus() == UserStatus.DISABLED)
            throw new AuthException(ErrorMessage.AUTH_DISABLED);
        if (user.getStatus() == UserStatus.RETIRED)
            throw new AuthException(ErrorMessage.AUTH_RETIRED);
        return user;
    }

    @Override
    public Optional<UserEntity> getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public UserEntity save(SignupRequest signup, UserRole role) {
        if (getByEmail(signup.getEmail()).isPresent()) {
            throw new DuplicatedException(ErrorMessage.AUTH_DUPLICATED_EMAIL);
        }
        UserEntity user = UserEntity.of(
                signup.getEmail(),
                signup.getPassword(),
                role
        );
        return userRepository.save(user);
    }

    @Override
    public void checkAccount(UserEntity user, String password) {
        if (EncryptUtil.matches(password, user.getPassword())) {
            if (user.getStatus() == UserStatus.DISABLED)
                throw new AuthException(ErrorMessage.AUTH_DISABLED);
            if (user.getStatus() == UserStatus.RETIRED)
                throw new AuthException(ErrorMessage.AUTH_RETIRED);
        } else
            throw new InvalidStateException(ErrorMessage.AUTH_INVALID_PASSWORD);
    }

    @Override
    @Transactional
    public void updatePassword(String userId, String oldPassword, String newPassword) {
        UserEntity user = getUser(userId);
        if (oldPassword != null) {
            checkAccount(user, oldPassword);
        }
        user.updatePassword(newPassword);
    }

    @Override
    public boolean getIsDuplicatedEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    @Transactional
    public void updateAvailableCount(String userId, int count) {
        UserEntity user = getUser(userId);
        int updatedCount = Math.max(
                user.getDailyAvailableCount() - count,
                0
        );
        user.updateAvailableCount(updatedCount);
    }

    @Override
    @Transactional
    public void saveExtractionLottos(String userId, RandomLottoListResponse randomLottos) {
        UserEntity user = getUser(userId);
        List<ExtractionHistoryEntity> entities = randomLottos.getLottoList()
                .stream()
                .map(it -> new ExtractionHistoryEntity(user, it))
                .collect(Collectors.toList());
        extractionHistoryRepository.saveAll(entities);
    }

    @Override
    public List<UserEntity> getAllEnabledUser() {
        return userRepository.findAllByStatusAndRole(
                UserStatus.ENABLED,
                UserRole.USER
        );
    }

    @Override
    @Transactional
    public void saveAll(List<UserEntity> users) {
        userRepository.saveAll(users);
    }

    @Override
    @Transactional
    public void retired(String userId) {
        UserEntity user = getUser(userId);
        user.updateStatus(UserStatus.RETIRED);
    }
}

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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ExtractionHistoryRepository extractionHistoryRepository;

    private final BCryptPasswordEncoder passwordEncoder;

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
        UserDetailResponse result = new UserDetailResponse();
        setUserDetail(user, result);
        return result;
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        UserEntity user = getByEmail(email).orElseThrow(
                () -> new NotFoundException(ErrorMessage.USER_NOT_FOUND)
        );
        if (user.getStatus() == UserStatus.DISABLED)
            throw new AuthException(ErrorMessage.AUTH_DISABLED);
        return user;
    }

    @Override
    public Optional<UserEntity> getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public boolean save(UserRole role, SignupRequest request) {
        if (getByEmail(request.getEmail()).isPresent()) {
            throw new DuplicatedException(ErrorMessage.AUTH_DUPLICATED_EMAIL);
        }
        UserEntity user = new UserEntity();
        setUser(user, role, request);
        userRepository.save(user);
        return true;
    }

    @Override
    public void checkAccount(UserEntity user, String password) {
        if (passwordEncoder.matches(password, user.getPassword())) {
            if (user.getStatus() == UserStatus.DISABLED)
                throw new AuthException(ErrorMessage.AUTH_DISABLED);
        } else
            throw new InvalidStateException(ErrorMessage.AUTH_INVALID_PASSWORD);
    }

    @Override
    @Transactional
    public boolean updatePassword(String userId, String oldPassword, String newPassword) {
        UserEntity user = getUser(userId);
        if (oldPassword != null) {
            checkAccount(user, oldPassword);
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean getIsDuplicatedEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    @Transactional
    public void updateAvailableCount(String userId, int count) {
        UserEntity user = getUser(userId);
        int oldCount = user.getDailyAvailableCount();
        user.setDailyAvailableCount(oldCount - count);
        userRepository.save(user);
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
        return userRepository.findAllByStatusAndRole(UserStatus.ENABLED, UserRole.USER);
    }

    @Override
    @Transactional
    public void saveAll(List<UserEntity> users) {
        userRepository.saveAll(users);
    }

    private void setUser(UserEntity entity, UserRole role, SignupRequest request) {
        entity.setRole(role);
        entity.setEmail(request.getEmail());
        entity.setPassword(passwordEncoder.encode(request.getPassword()));
    }

    private void setUserDetail(UserEntity user, UserDetailResponse result) {
        result.setEmail(user.getEmail());
        result.setCreatedAt(user.getCreatedAt());
        result.setRole(user.getRole());
    }
}

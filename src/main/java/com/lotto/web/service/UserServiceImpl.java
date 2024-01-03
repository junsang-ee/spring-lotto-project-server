package com.lotto.web.service;

import com.lotto.web.constants.UserRole;
import com.lotto.web.constants.UserStatus;
import com.lotto.web.constants.messages.ErrorMessage;
import com.lotto.web.exception.custom.AuthException;
import com.lotto.web.exception.custom.DuplicatedException;
import com.lotto.web.exception.custom.InvalidStateException;
import com.lotto.web.exception.custom.NotFoundException;
import com.lotto.web.model.dto.request.LoginRequest;
import com.lotto.web.model.dto.request.SignupRequest;
import com.lotto.web.model.dto.response.UserDetailResponse;
import com.lotto.web.model.entity.UserEntity;
import com.lotto.web.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Optional<UserEntity> get(String userId) {
        return userRepository.findById(userId);
    }

    @Override
    public UserEntity getUser(String userId) {
        return get(userId).orElseThrow(
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
    public Optional<UserEntity> getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public UserEntity save(UserRole role, SignupRequest request) {
        if (getByEmail(request.getEmail()).isPresent()) {
            throw new DuplicatedException(ErrorMessage.AUTH_DUPLICATED_EMAIL);
        }
        UserEntity user = new UserEntity();
        setUser(user, role, request);
        return userRepository.save(user);
    }

    @Override
    public UserEntity checkAccount(String userId, LoginRequest login) {
        String email = login.getEmail();
        String password = login.getPassword();
        UserEntity user = userRepository.findByIdOrEmail(userId, email).orElseThrow(
                () -> new NotFoundException(ErrorMessage.USER_NOT_FOUND)
        );

        if (passwordEncoder.matches(password, user.getPassword())) {
            log.info("password matched!");
            if (user.getStatus() == UserStatus.DISABLED)
                throw new AuthException(ErrorMessage.AUTH_DISABLED);
            return user;
        }
        throw new InvalidStateException(ErrorMessage.AUTH_INVALID_PASSWORD);
    }

    private void setUser(UserEntity entity, UserRole role, SignupRequest request) {
        entity.setRole(role);
        entity.setEmail(request.getEmail());
        entity.setPassword(passwordEncoder.encode(request.getPassword()));
    }

    private void setUserDetail(UserEntity user, UserDetailResponse result) {
        result.setEmail(user.getEmail());
        result.setCreatedAt(user.getCreatedAt());
    }
}

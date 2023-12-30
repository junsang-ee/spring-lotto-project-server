package com.lotto.web.service;

import com.lotto.web.constants.UserRole;
import com.lotto.web.constants.messages.ErrorMessage;
import com.lotto.web.exception.custom.DuplicatedException;
import com.lotto.web.exception.custom.NotFoundException;
import com.lotto.web.model.dto.request.SignupRequest;
import com.lotto.web.model.entity.UserEntity;
import com.lotto.web.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

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
        return userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException(ErrorMessage.USER_NOT_FOUND)
        );
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

    private void setUser(UserEntity entity, UserRole role, SignupRequest request) {
        entity.setRole(role);
        entity.setEmail(request.getEmail());
        entity.setPassword(passwordEncoder.encode(request.getPassword()));
    }
}

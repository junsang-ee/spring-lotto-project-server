package com.lotto.web.service;

import com.lotto.web.model.entity.UserEntity;
import com.lotto.web.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
}

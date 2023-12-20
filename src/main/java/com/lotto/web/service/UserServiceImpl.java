package com.lotto.web.service;

import com.lotto.web.model.entity.UserEntity;
import com.lotto.web.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Override
    public UserEntity get(String userId) {
        return null;
    }
}

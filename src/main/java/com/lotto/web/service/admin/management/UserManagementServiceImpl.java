package com.lotto.web.service.admin.management;

import com.lotto.web.constants.UserStatus;
import com.lotto.web.constants.messages.ErrorMessage;
import com.lotto.web.exception.custom.AuthException;
import com.lotto.web.exception.custom.NotFoundException;
import com.lotto.web.model.dto.response.admin.UserManageDetailResponse;
import com.lotto.web.model.entity.UserEntity;
import com.lotto.web.repository.UserRepository;
import com.lotto.web.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserManagementServiceImpl implements UserManagementService {

    private final AdminService adminService;

    private final UserRepository userRepository;

    @Override
    public UserEntity get(String userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException(ErrorMessage.USER_NOT_FOUND)
        );
    }

    @Override
    public Page<UserManageDetailResponse> list(Pageable pageable) {
        Page<UserManageDetailResponse> list = userRepository.getAllUser(
                adminService.getAdmin(),
                pageable
        );
        return new PageImpl<>(
                list.stream().collect(Collectors.toList()),
                list.getPageable(),
                list.getTotalElements()
        );
    }

    @Override
    @Transactional
    public boolean updateStatus(String userId, UserStatus status) {
        UserEntity user = get(userId);
        validStatus(user, status);
        user.setStatus(status);
        userRepository.save(user);
        return true;
    }

    private void validStatus(UserEntity user, UserStatus status) {
        if (user.getStatus() == status) {
            switch (user.getStatus()) {
                case DISABLED:
                    throw new AuthException(ErrorMessage.AUTH_DISABLED);
                case RETIRED:
                    throw new AuthException(ErrorMessage.AUTH_RETIRED);
                case ENABLED:
                    throw new AuthException(ErrorMessage.AUTH_ENABLED);
            }
        }
    }
}

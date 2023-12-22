package com.lotto.web.security;

import com.lotto.web.model.entity.UserEntity;
import com.lotto.web.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class UserDetailServiceImpl implements ExtendedUserDetailsService {

    private final Supplier<UserService> serviceSupplier;
    @Override
    public UserDetails getByUserId(String userId) throws UsernameNotFoundException {
        return serviceSupplier.get().get(userId).map(this::convert)
                .orElseThrow(() -> new UsernameNotFoundException("로 등록된 사용자 정보를 찾을 수 없습니다."));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    private UserDetails convert(UserEntity entity){
        return new ExtendedUserDetails(entity.getId(), entity.getEmail(), entity.getRole());
    }
}

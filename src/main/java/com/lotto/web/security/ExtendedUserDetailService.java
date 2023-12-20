package com.lotto.web.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface ExtendedUserDetailService extends UserDetailsService {
    UserDetails getByUserId(String userId) throws UsernameNotFoundException;
}

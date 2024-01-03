package com.lotto.web.security;

import com.lotto.web.constants.UserRole;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

@Getter
@EqualsAndHashCode(callSuper = true)
public class ExtendedUserDetails extends User {

    private final String id;

    public ExtendedUserDetails(String id, String email, UserRole role){
        super(email, "", AuthorityUtils.createAuthorityList("ROLE_" + role.name()));
        this.id = id;
    }

    public ExtendedUserDetails(String id, String email, UserRole role, boolean enabled){
        super(email, "", enabled, true, true, true,
                AuthorityUtils.createAuthorityList("ROLE_" + role.name()));
        this.id = id;
    }
}

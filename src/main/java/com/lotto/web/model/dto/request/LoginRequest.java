package com.lotto.web.model.dto.request;


import com.lotto.web.constants.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequest {
    private String email;
    private String password;
    private UserRole role;
}

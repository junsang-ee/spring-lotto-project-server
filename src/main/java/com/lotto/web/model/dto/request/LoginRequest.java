package com.lotto.web.model.dto.request;

import com.lotto.web.config.validator.BasicValidator;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequest {

    @BasicValidator(fieldName = "Email")
    private String email;

    @BasicValidator(fieldName = "Password")
    private String password;
}

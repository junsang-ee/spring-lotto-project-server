package com.lotto.web.model.dto.request;

import com.lotto.web.config.validator.BasicValidator;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VerifyAuthRequest {

    @BasicValidator(fieldName = "Email")
    private String email;

    @BasicValidator(fieldName = "AuthCode")
    private String authCode;
}

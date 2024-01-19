package com.lotto.web.model.dto.request;

import com.lotto.web.config.validator.BasicValidator;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VerifyEmailRequest {

    @BasicValidator(fieldName = "Email")
    private String email;
}

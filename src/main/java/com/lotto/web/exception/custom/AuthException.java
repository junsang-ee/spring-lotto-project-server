package com.lotto.web.exception.custom;

import com.lotto.web.constants.messages.ErrorMessage;

public class AuthException extends BaseException {
    public AuthException(ErrorMessage error) {
        super(error);
    }
}

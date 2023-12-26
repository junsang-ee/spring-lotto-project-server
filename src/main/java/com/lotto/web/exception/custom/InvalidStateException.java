package com.lotto.web.exception.custom;

import com.lotto.web.constants.messages.ErrorMessage;

public class InvalidStateException extends BaseException {
    public InvalidStateException(ErrorMessage error) {
        super(error);
    }
}

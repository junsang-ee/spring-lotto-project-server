package com.lotto.web.exception.custom;

import com.lotto.web.constants.messages.ErrorMessage;

public class NotFoundException extends BaseException {
    public NotFoundException(ErrorMessage error) {
        super(error);
    }
}

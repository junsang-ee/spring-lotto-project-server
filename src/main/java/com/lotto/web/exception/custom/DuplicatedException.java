package com.lotto.web.exception.custom;

import com.lotto.web.constants.messages.ErrorMessage;

public class DuplicatedException extends BaseException {

    public DuplicatedException(ErrorMessage error) {
        super(error);
    }

}

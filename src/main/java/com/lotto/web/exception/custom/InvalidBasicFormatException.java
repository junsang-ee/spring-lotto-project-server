package com.lotto.web.exception.custom;

import com.lotto.web.constants.messages.ErrorMessage;
import lombok.Getter;

@Getter
public class InvalidBasicFormatException extends BaseException {

    private String fieldName;

    public InvalidBasicFormatException(ErrorMessage error) {
        super(error);
    }

    public InvalidBasicFormatException(ErrorMessage error, String fieldName) {
        super(error);
        if (fieldName != null)
            this.fieldName = fieldName;
    }
}

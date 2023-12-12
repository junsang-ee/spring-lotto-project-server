package com.lotto.web.exception.custom;

import com.lotto.web.constants.messages.ErrorMessage;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.core.NestedRuntimeException;

@Getter
public class BaseException extends NestedRuntimeException {
    
    @Accessors(fluent = true)
    private ErrorMessage error;

    public BaseException(String reason) {
        super(reason);
    }

    public BaseException(ErrorMessage error) {
        super(error.code());
        this.error = error;
    }
}

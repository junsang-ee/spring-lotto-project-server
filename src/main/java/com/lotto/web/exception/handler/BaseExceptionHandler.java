package com.lotto.web.exception.handler;

import com.lotto.web.constants.messages.ErrorMessage;
import com.lotto.web.exception.custom.BaseException;
import com.lotto.web.model.dto.response.common.ApiErrorResponse;
import com.lotto.web.util.MessageUtil;
import org.springframework.lang.Nullable;


public class BaseExceptionHandler {

    public String getMessage(String target, @Nullable String[] args) {
        return MessageUtil.getMessage(target, args);
    }

    public ApiErrorResponse toResponse(BaseException ex) {
        ErrorMessage error = ex.error();
        return toResponse(error, null);
    }

    public ApiErrorResponse toResponse(ErrorMessage error, String[] args) {
        String message = getMessage(error.resName(), args);
        return new ApiErrorResponse(error.getCode(), message);
    }
}

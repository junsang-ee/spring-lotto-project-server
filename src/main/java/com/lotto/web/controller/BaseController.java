package com.lotto.web.controller;

import com.lotto.web.constants.messages.SuccessMessage;
import com.lotto.web.model.dto.response.common.ApiSuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Optional;

public class BaseController {

    @Autowired
    private MessageSource messageSource;

    public <T> ApiSuccessResponse<T> wrap(T data) {
        SuccessMessage success = SuccessMessage.EMPTY;

        if (data != null) success = SuccessMessage.RESULT;

        String message = messageSource.getMessage(success.resName(), null, LocaleContextHolder.getLocale());

        return new ApiSuccessResponse(success.getCode(), message, data);
    }

    public <T> ApiSuccessResponse<T> optional(Optional<T> data) {
        if (data.isPresent()) {
            return wrap(data.get());
        } else {
            return wrap(null);
        }
    }

}

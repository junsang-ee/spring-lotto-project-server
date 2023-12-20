package com.lotto.web.controller;

import com.lotto.web.constants.messages.SuccessMessage;
import com.lotto.web.model.dto.response.common.ApiSuccessResponse;
import com.lotto.web.util.MessageUtil;

import java.util.Optional;

public class BaseController {

    public <T> ApiSuccessResponse<T> wrap(T data) {
        SuccessMessage success = SuccessMessage.EMPTY;

        if (data != null) {
            success = SuccessMessage.RESULT;
        }

        return new ApiSuccessResponse<>(success.getCode(), MessageUtil.getMessage(success.resName()), data);
    }

    public <T> ApiSuccessResponse<T> optional(Optional<T> data) {
        if (data.isPresent()) {
            return wrap(data.get());
        } else {
            return wrap(null);
        }
    }

}

package com.lotto.web.controller;

import com.lotto.web.constants.messages.SuccessMessage;
import com.lotto.web.model.dto.response.common.ApiSuccessResponse;
import com.lotto.web.model.dto.response.common.PageResponse;
import com.lotto.web.util.MessageUtil;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public class BaseController {

    public <T> ApiSuccessResponse<T> wrap(T data) {
        SuccessMessage success = SuccessMessage.EMPTY;

        if (data instanceof PageResponse && ((PageResponse<?>) data).getTotalCount() > 0) {
            success = SuccessMessage.RESULT;
        } else if (data instanceof List && ((List<?>) data).size() > 0) {
            success = SuccessMessage.RESULT;
        } else if (data != null) {
            success = SuccessMessage.RESULT;
        }

        return new ApiSuccessResponse<>(success.getCode(), MessageUtil.getMessage(success.resName()), data);
    }

    public <T> ApiSuccessResponse<PageResponse<T>> page(Page<T> data) {
        return wrap(new PageResponse<>(data));
    }

    public <T> ApiSuccessResponse<T> optional(Optional<T> data) {
        if (data.isPresent()) {
            return wrap(data.get());
        } else {
            return wrap(null);
        }
    }

}

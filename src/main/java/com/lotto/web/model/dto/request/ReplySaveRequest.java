package com.lotto.web.model.dto.request;

import com.lotto.web.config.validator.BasicValidator;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReplySaveRequest {

    @BasicValidator(fieldName = "ReplyContent")
    private String content;
}

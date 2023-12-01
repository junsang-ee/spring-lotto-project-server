package com.lotto.web.constants.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@AllArgsConstructor
public enum ErrorMessage {
    NEEDS_NUMBER_OVER("100"),
    EXCEPTION_NUMBER_OVER("101"),
    PRICE_UNIT_INCONSISTENT("103"),
    USER_NOT_FOUND("500");

    @Accessors(fluent = true)
    private final String code;
}

package com.lotto.web.constants.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {
    NEEDS_NUMBER_OVER("100"),
    EXCEPTION_NUMBER_OVER("101"),
    PRICE_UNIT_INCONSISTENT("103"),
    USER_NOT_FOUND("500"),
    UNKNOWN("9999");


    private final String code;

    public String resName() {
        return "response.error." + name().toLowerCase().replaceAll("_", ".") + ".message";
    }

    public int getCode() {
        return Integer.parseInt(code);
    }

    public static ErrorMessage from(String code) {
        for (ErrorMessage value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException(code);
    }
}

package com.lotto.web.constants.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;


@Getter
@AllArgsConstructor
public enum ErrorMessage {

    /* 1000 ~ LottoError */
    OVER_NEEDS_NUMBER("1000"),
    OVER_EXCEPTION_NUMBER("1001"),
    PRICE_UNIT_INCONSISTENT("1003"),

    /* 4000 ~ AuthError*/
    AUTH_DUPLICATED_EMAIL("4000"),

    /* 5000 ~ UserError */
    USER_NOT_FOUND("5000"),

    /* 6000 ~ BoardError (board) */
    BOARD_NOT_FOUND("6000"),
    BOARD_ALREADY_REMOVED("6001"),

    /* 7000 ~ PostError (post) */
    POST_NOT_FOUND("7000"),





    /* 9999 UknownError */
    UNKNOWN("9999");

    @Accessors(fluent = true)
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

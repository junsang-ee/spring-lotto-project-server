package com.lotto.web.constants.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;


@Getter
@AllArgsConstructor
public enum ErrorMessage {

    REQUEST_QUERY_PARAM("100"),

    /* 1000 ~ LottoError */
    LOTTO_OVER_NEEDS("1000"),
    LOTTO_OVER_EXCEPTION("1001"),
    LOTTO_PRICE_UNIT("1002"),

    /* 4000 ~ AuthError*/
    AUTH_DUPLICATED_EMAIL("4000"),
    AUTH_DISABLED("4001"),
    AUTH_INVALID_PASSWORD("4002"),

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

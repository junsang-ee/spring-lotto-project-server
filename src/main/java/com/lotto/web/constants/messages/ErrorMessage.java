package com.lotto.web.constants.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;


@Getter
@AllArgsConstructor
public enum ErrorMessage {

    REQUEST_QUERY_PARAM("100"),
    REQUEST_BODY_FIELD("101"),
    REQUEST_ARGUMENTS_NOT_BLANK("102"),
    REQUEST_ARGUMENTS_NOT_NULLABLE("103"),
    REQUEST_ARGUMENTS_NOT_EMPTY("104"),
    REQUEST_INVALID_EMAIL("105"),

    /* 1000 ~ LottoError */
    LOTTO_OVER_NEEDS("1000"),
    LOTTO_OVER_EXCEPTION("1001"),
    LOTTO_PRICE_UNIT("1002"),

    /* 4000 ~ AuthError*/
    AUTH_DUPLICATED_EMAIL("4000"),
    AUTH_DISABLED("4001"),
    AUTH_INVALID_PASSWORD("4002"),
    AUTH_ONLY_ADMIN("4003"),
    AUTH_ONLY_USER("4004"),

    /* 5000 ~ UserError */
    USER_NOT_FOUND("5000"),

    /* 6000 ~ BoardError (board) */
    BOARD_NOT_FOUND("6000"),
    BOARD_REMOVED("6001"),

    /* 7000 ~ PostError (post) */
    POST_NOT_FOUND("7000"),
    POST_INVALID_PASSWORD("7001"),
    POST_DISABLED("7002"),
    POST_REMOVED("7003"),
    POST_ONLY_EDIT_WRITER("7004"),
    POST_ONLY_REMOVE_WRITER("7005"),

    /* 8000 ~ PostError (reply) */
    REPLY_NOT_FOUND("8000"),
    REPLY_DISABLED("8001"),
    REPLY_REMOVED("8002"),
    REPLY_ONLY_EDIT_WRITER("8003"),
    REPLY_ONLY_REMOVE_WRITER("8004"),

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

package com.lotto.web.model.dto.response.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public abstract class ApiResponse {
    private final int code;

    private final String message;

}

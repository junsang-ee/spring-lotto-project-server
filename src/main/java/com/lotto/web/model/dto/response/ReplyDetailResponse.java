package com.lotto.web.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReplyDetailResponse {

    private String id;

    private String writer;

    private String content;

    private boolean mine;
}

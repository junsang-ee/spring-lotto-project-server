package com.lotto.web.model.dto.response;

import com.lotto.web.constants.PostActivationStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReplySaveResponse {
    private String id;
    private String content;
    private PostActivationStatus status;
    private String parentPostTitle;
    private String writer;
}

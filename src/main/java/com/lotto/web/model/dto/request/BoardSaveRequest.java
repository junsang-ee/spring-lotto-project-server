package com.lotto.web.model.dto.request;

import com.lotto.web.constants.BoardAccessType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardSaveRequest {
    private String name;
    private BoardAccessType accessType;
}

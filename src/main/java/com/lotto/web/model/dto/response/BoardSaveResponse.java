package com.lotto.web.model.dto.response;

import com.lotto.web.constants.BoardAccessType;
import com.lotto.web.constants.BoardActivationStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class BoardSaveResponse {
    private String name;
    private BoardAccessType type;
    private BoardActivationStatus status;
    private Instant createdAt;
}

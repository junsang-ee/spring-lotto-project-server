package com.lotto.web.model.dto.request;

import com.lotto.web.constants.BoardActivationStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardStatusRequest {
    private BoardActivationStatus status;
}

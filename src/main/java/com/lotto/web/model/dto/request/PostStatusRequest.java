package com.lotto.web.model.dto.request;

import com.lotto.web.constants.PostActivationStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostStatusRequest {
    private PostActivationStatus status;
}

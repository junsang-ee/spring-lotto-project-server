package com.lotto.web.model.dto.response.admin;

import com.lotto.web.constants.BoardAccessType;
import com.lotto.web.constants.BoardActivationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BoardDetailResponse {
    private String id;
    private String name;
    private BoardActivationStatus status;
    private BoardAccessType accessType;

}

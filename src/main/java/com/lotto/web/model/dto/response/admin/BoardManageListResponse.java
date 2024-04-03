package com.lotto.web.model.dto.response.admin;

import com.lotto.web.constants.BoardAccessType;
import com.lotto.web.constants.BoardActivationStatus;
import com.lotto.web.model.entity.BoardEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public class BoardManageListResponse {
    private final String id;
    private final String name;
    private final BoardActivationStatus status;
    private final BoardAccessType accessType;
    private final int enabledPostCount;
    private final int disabledPostCount;

    public static BoardManageListResponse of(final BoardEntity board) {
        return new BoardManageListResponse(
                board.getId(),
                board.getName(),
                board.getStatus(),
                board.getAccessType(),
                board.getPostCount().getEnabledCount(),
                board.getPostCount().getDisabledCount()
        );
    }
}

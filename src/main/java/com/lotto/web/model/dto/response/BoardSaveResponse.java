package com.lotto.web.model.dto.response;

import com.lotto.web.constants.BoardAccessType;
import com.lotto.web.constants.BoardActivationStatus;
import com.lotto.web.model.entity.BoardEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public class BoardSaveResponse {
    private final String name;
    private final BoardAccessType type;
    private final BoardActivationStatus status;
    private final Instant createdAt;

    public static BoardSaveResponse of(final BoardEntity board) {
        return new BoardSaveResponse(
                board.getName(),
                board.getAccessType(),
                board.getStatus(),
                board.getCreatedAt()
        );
    }
}

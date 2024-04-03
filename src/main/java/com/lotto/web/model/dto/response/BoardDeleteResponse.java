package com.lotto.web.model.dto.response;

import com.lotto.web.model.entity.BoardEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static lombok.AccessLevel.PRIVATE;
@Getter
@RequiredArgsConstructor(access = PRIVATE)
public class BoardDeleteResponse {
    private final String name;

    public static BoardDeleteResponse of(final BoardEntity board) {
        return new BoardDeleteResponse(board.getName());
    }
}

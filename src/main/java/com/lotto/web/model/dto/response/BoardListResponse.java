package com.lotto.web.model.dto.response;

import com.lotto.web.model.entity.BoardEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public class BoardListResponse {
    private final String id;
    private final String name;

    public static BoardListResponse of(final BoardEntity board) {
        return new BoardListResponse(
                board.getId(),
                board.getName()
        );
    }
}

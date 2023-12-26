package com.lotto.web.service;

import com.lotto.web.model.dto.request.BoardSaveRequest;
import com.lotto.web.model.dto.response.BoardListResponse;
import com.lotto.web.model.dto.response.BoardSaveResponse;
import com.lotto.web.model.entity.BoardEntity;

public interface BoardService {
    BoardEntity get(String boardId);

    BoardSaveResponse save(BoardSaveRequest request);

    BoardListResponse listAll();

    BoardListResponse listForUser();

    boolean deleteBoard(String boardId);
}

package com.lotto.web.service;

import com.lotto.web.model.dto.request.BoardSaveRequest;
import com.lotto.web.model.dto.response.BoardListResponse;
import com.lotto.web.model.entity.BoardEntity;

public interface BoardService {
    BoardEntity get(String boardId);

    BoardEntity save(String userId, BoardSaveRequest request);

    BoardListResponse listAll();

    BoardListResponse listForUser();

    boolean deleteBoard(String boardId);
}

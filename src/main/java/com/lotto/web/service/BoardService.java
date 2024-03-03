package com.lotto.web.service;

import com.lotto.web.model.dto.response.BoardListResponse;
import com.lotto.web.model.entity.BoardEntity;
import com.lotto.web.model.entity.count.PostCountEntity;

public interface BoardService {
    BoardEntity get(String boardId);

    BoardListResponse listAll();

    BoardListResponse listForUser();

    void updatePostCount(BoardEntity board, PostCountEntity postCount);
}

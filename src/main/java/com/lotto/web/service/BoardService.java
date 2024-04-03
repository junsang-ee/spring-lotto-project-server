package com.lotto.web.service;

import com.lotto.web.model.dto.response.BoardListResponse;
import com.lotto.web.model.entity.BoardEntity;
import com.lotto.web.model.entity.count.PostCountEntity;

import java.util.List;

public interface BoardService {
    BoardEntity get(String boardId);

    List<BoardListResponse> list();
}

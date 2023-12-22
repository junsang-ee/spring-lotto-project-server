package com.lotto.web.service;

import com.lotto.web.model.dto.request.BoardSaveRequest;
import com.lotto.web.model.dto.response.BoardListResponse;
import com.lotto.web.model.dto.response.BoardSaveResponse;

import java.util.List;

public interface BoardService {
    BoardSaveResponse save(BoardSaveRequest request);

    BoardListResponse listAll();

    BoardListResponse listForUser();
}

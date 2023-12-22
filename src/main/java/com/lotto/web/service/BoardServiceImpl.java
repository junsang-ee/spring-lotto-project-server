package com.lotto.web.service;

import com.lotto.web.constants.BoardAccessType;
import com.lotto.web.model.dto.request.BoardSaveRequest;
import com.lotto.web.model.dto.response.BoardDetailResponse;
import com.lotto.web.model.dto.response.BoardListResponse;
import com.lotto.web.model.dto.response.BoardSaveResponse;
import com.lotto.web.model.entity.BoardEntity;
import com.lotto.web.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    @Override
    public BoardSaveResponse save(BoardSaveRequest request) {
        BoardEntity entity = new BoardEntity();
        setBoardEntity(entity, request);
        BoardEntity savedEntity = boardRepository.save(entity);
        BoardSaveResponse result = new BoardSaveResponse();
        setBoardResponse(savedEntity, result);
        return result;
    }

    @Override
    public BoardListResponse listAll() {
//        List<BoardDetailResponse> boardDetails = boardRepository.getAll();
//        BoardListResponse result = new BoardListResponse();
//        setBoardList(boardDetails, result);
        return null;
    }

    @Override
    public BoardListResponse listForUser() {
//        List<BoardDetailResponse> boardDetails =
//                boardRepository.getAllByBoardAccessType(BoardAccessType.ALL);
//        BoardListResponse result = new BoardListResponse();
//        setBoardList(boardDetails, result);
        return null;
    }

    private void setBoardResponse(BoardEntity entity, BoardSaveResponse response) {
        response.setBoardName(entity.getName());
    }

    private void setBoardEntity(BoardEntity entity,
                                BoardSaveRequest request) {
        entity.setName(request.getName());
        entity.setAccessType(request.getAccessType());
    }

    private void setBoardList(List<BoardDetailResponse> boardDetails,
                              BoardListResponse response) {
        response.setBoards(boardDetails);

    }
}

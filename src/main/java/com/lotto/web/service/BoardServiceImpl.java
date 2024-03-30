package com.lotto.web.service;

import com.lotto.web.constants.BoardAccessType;
import com.lotto.web.constants.BoardActivationStatus;
import com.lotto.web.constants.messages.ErrorMessage;
import com.lotto.web.exception.custom.NotFoundException;
import com.lotto.web.model.dto.response.BoardListEntryResponse;
import com.lotto.web.model.dto.response.BoardListResponse;
import com.lotto.web.model.entity.BoardEntity;
import com.lotto.web.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Override
    public BoardEntity get(String boardId) {
        return boardRepository.findById(boardId).orElseThrow(
                () -> new NotFoundException(ErrorMessage.BOARD_NOT_FOUND)
        );
    }

    @Override
    public BoardListResponse listAll() {
        List<BoardListEntryResponse> boardDetails =
                boardRepository.getAllByStatus(BoardActivationStatus.NORMAL);
        BoardListResponse result = new BoardListResponse();
        setBoardList(boardDetails, result);
        return result;
    }

    @Override
    public BoardListResponse listForUser() {
        List<BoardListEntryResponse> boardDetails =
                boardRepository.getAllByBoardByAccessTypeAndStatus(
                        BoardAccessType.USER,
                        BoardActivationStatus.NORMAL
                );
        BoardListResponse result = new BoardListResponse();
        setBoardList(boardDetails, result);
        return result;
    }

    private void setBoardList(List<BoardListEntryResponse> boardDetails,
                              BoardListResponse response) {
        response.setBoards(boardDetails);
    }
}

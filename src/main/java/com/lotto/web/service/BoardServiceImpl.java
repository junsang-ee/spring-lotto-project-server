package com.lotto.web.service;

import com.lotto.web.constants.BoardAccessType;
import com.lotto.web.constants.BoardActivationStatus;
import com.lotto.web.constants.messages.ErrorMessage;
import com.lotto.web.exception.custom.InvalidStateException;
import com.lotto.web.exception.custom.NotFoundException;
import com.lotto.web.model.dto.request.BoardSaveRequest;
import com.lotto.web.model.dto.response.BoardListEntryResponse;
import com.lotto.web.model.dto.response.BoardListResponse;
import com.lotto.web.model.dto.response.BoardSaveResponse;
import com.lotto.web.model.entity.BoardEntity;
import com.lotto.web.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
    @Transactional
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
                        BoardAccessType.ALL,
                        BoardActivationStatus.NORMAL
                );
        BoardListResponse result = new BoardListResponse();
        setBoardList(boardDetails, result);
        return result;
    }

    @Override
    @Transactional
    public boolean deleteBoard(String boardId) {
        BoardEntity entity = get(boardId);

        if (entity.getStatus() == BoardActivationStatus.ERASED) {
            throw new InvalidStateException(ErrorMessage.BOARD_ALREADY_REMOVED);
        }
        entity.setStatus(BoardActivationStatus.ERASED);
        boardRepository.save(entity);
        return true;
    }

    private void setBoardResponse(BoardEntity entity, BoardSaveResponse response) {
        response.setBoardName(entity.getName());
    }

    private void setBoardEntity(BoardEntity entity,
                                BoardSaveRequest request) {
        entity.setName(request.getName());
        entity.setAccessType(request.getAccessType());
    }

    private void setBoardList(List<BoardListEntryResponse> boardDetails,
                              BoardListResponse response) {
        response.setBoards(boardDetails);
    }

    private void exception(String code) {
        ErrorMessage errorMessage = ErrorMessage.from(code);
        throw new InvalidStateException(errorMessage);
    }
}

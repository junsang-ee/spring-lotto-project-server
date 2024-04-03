package com.lotto.web.service.admin.management;

import com.lotto.web.constants.BoardActivationStatus;
import com.lotto.web.constants.messages.ErrorMessage;
import com.lotto.web.exception.custom.InvalidStateException;
import com.lotto.web.exception.custom.NotFoundException;
import com.lotto.web.model.dto.request.BoardSaveRequest;
import com.lotto.web.model.dto.response.BoardDeleteResponse;
import com.lotto.web.model.dto.response.BoardSaveResponse;
import com.lotto.web.model.dto.response.admin.BoardManageListResponse;
import com.lotto.web.model.entity.BoardEntity;
import com.lotto.web.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BoardManagementServiceImpl implements BoardManagementService {

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
        BoardEntity board = BoardEntity.of(request);
        return BoardSaveResponse.of(boardRepository.save(board));
    }

    @Override
    @Transactional
    public BoardDeleteResponse delete(String boardId) {
        BoardEntity board = get(boardId);
        boardRepository.delete(board);
        BoardDeleteResponse result = new BoardDeleteResponse();
        result.setName(board.getName());
        return result;
    }

    @Override
    @Transactional
    public boolean updateStatus(String boardId, BoardActivationStatus status) {
        BoardEntity board = get(boardId);
        validStatus(board, status);
        board.updateStatus(status);
        return true;
    }

    @Override
    public Page<BoardManageListResponse> list(Pageable pageable) {
        return boardRepository.findAll(pageable)
                .map(BoardManageListResponse::of);
    }

    private void validStatus(BoardEntity board, BoardActivationStatus status) {
        if (board.getStatus() == status) {
            if (board.getStatus() == BoardActivationStatus.REMOVED)
                throw new InvalidStateException(ErrorMessage.BOARD_REMOVED);
            else if (board.getStatus() == BoardActivationStatus.NORMAL)
                throw new InvalidStateException(ErrorMessage.BOARD_ENABLED);
        }
    }
}

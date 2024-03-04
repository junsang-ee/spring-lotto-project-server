package com.lotto.web.service.admin.management;

import com.lotto.web.constants.BoardActivationStatus;
import com.lotto.web.constants.messages.ErrorMessage;
import com.lotto.web.exception.custom.InvalidStateException;
import com.lotto.web.exception.custom.NotFoundException;
import com.lotto.web.model.dto.request.BoardSaveRequest;
import com.lotto.web.model.dto.response.BoardDeleteResponse;
import com.lotto.web.model.dto.response.BoardSaveResponse;
import com.lotto.web.model.dto.response.admin.BoardManageDetailResponse;
import com.lotto.web.model.entity.BoardEntity;
import com.lotto.web.repository.BoardRepository;
import com.lotto.web.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BoardManagementServiceImpl implements BoardManagementService {

    private final AdminService adminService;

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
        BoardEntity board = new BoardEntity();
        setBoard(board, request);
        BoardEntity savedBoard = boardRepository.save(board);
        BoardSaveResponse result = new BoardSaveResponse();
        setSaveResponse(result, savedBoard);
        return result;
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
        board.setStatus(status);
        boardRepository.save(board);
        return true;
    }

    @Override
    public Page<BoardManageDetailResponse> list(Pageable pageable) {
        Page<BoardManageDetailResponse> list = boardRepository.getAllBoard(pageable);
        return new PageImpl<>(
                list.stream().collect(Collectors.toList()),
                list.getPageable(),
                list.getTotalElements()
        );
    }

    private void setBoard(BoardEntity board, BoardSaveRequest request) {
        board.setCreatedBy(adminService.getAdmin());
        board.setName(request.getName());
        board.setAccessType(request.getAccessType());
    }

    private void setSaveResponse(BoardSaveResponse result, BoardEntity board) {
        result.setName(board.getName());
        result.setType(board.getAccessType());
        result.setStatus(board.getStatus());
        result.setCreatedAt(board.getCreatedAt());
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

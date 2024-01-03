package com.lotto.web.service;

import com.lotto.web.constants.BoardAccessType;
import com.lotto.web.constants.BoardActivationStatus;
import com.lotto.web.constants.UserRole;
import com.lotto.web.constants.messages.ErrorMessage;
import com.lotto.web.exception.custom.AuthException;
import com.lotto.web.exception.custom.InvalidStateException;
import com.lotto.web.exception.custom.NotFoundException;
import com.lotto.web.model.dto.request.BoardSaveRequest;
import com.lotto.web.model.dto.response.BoardListEntryResponse;
import com.lotto.web.model.dto.response.BoardListResponse;
import com.lotto.web.model.entity.BoardEntity;
import com.lotto.web.model.entity.UserEntity;
import com.lotto.web.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    private final UserService userService;

    @Override
    public BoardEntity get(String boardId) {
        return boardRepository.findById(boardId).orElseThrow(
                () -> new NotFoundException(ErrorMessage.BOARD_NOT_FOUND)
        );
    }

    @Override
    @Transactional
    public boolean save(String userId, BoardSaveRequest request) {
        UserEntity user = userService.getUser(userId);
        if (user.getRole() != UserRole.ADMIN) {
            throw new AuthException(ErrorMessage.AUTH_ONLY_ADMIN);
        }
        BoardEntity board = new BoardEntity();
        setBoardEntity(user, board, request);
        boardRepository.save(board);
        return true;
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

    private void setBoardEntity(UserEntity user,
                                BoardEntity entity,
                                BoardSaveRequest request) {
        entity.setCreatedBy(user);
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

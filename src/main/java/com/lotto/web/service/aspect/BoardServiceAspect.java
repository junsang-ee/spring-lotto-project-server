package com.lotto.web.service.aspect;

import com.lotto.web.constants.BoardAccessType;
import com.lotto.web.constants.cache.CacheType;
import com.lotto.web.model.dto.request.BoardSaveRequest;
import com.lotto.web.model.dto.response.BoardListResponse;
import com.lotto.web.model.entity.BoardEntity;
import com.lotto.web.service.BoardService;
import com.lotto.web.service.caffeine.CaffeineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Aspect
@Component
public class BoardServiceAspect {

    private final CaffeineService caffeineService;

    private final BoardService boardService;
    private static final String USER_BOARD_KEY = "user_boards";

    @AfterReturning(value = "execution(* com..AdminService.saveBoard(..)) && args(request)",
            returning = "board", argNames = "request, board")
    public void updateCachedBoards(BoardSaveRequest request, BoardEntity board) {
        if (request.getAccessType() != BoardAccessType.USER)
            return;
        try {
            if (caffeineService.get(CacheType.USER_BOARDS, USER_BOARD_KEY) != null) {
                caffeineService.remove(CacheType.USER_BOARDS, USER_BOARD_KEY);
            }
        } catch (NullPointerException ignored) {
        } finally {
            BoardListResponse boards = boardService.listForUser();
            if (boards != null && !boards.getBoards().isEmpty()) {
                caffeineService.put(
                        CacheType.USER_BOARDS,
                        USER_BOARD_KEY,
                        boards
                );
            }
        }
    }

    @AfterReturning(value = "execution(* com..AdminService.deleteBoard(..)) && args(boardId)",
            returning = "isDelete", argNames = "boardId, isDelete")
    public void updateCachedBoards(String boardId, boolean isDelete) {
        if (boardService.get(boardId).getAccessType() == BoardAccessType.ADMIN)
            return;
        try {
            if (caffeineService.get(CacheType.USER_BOARDS, USER_BOARD_KEY) != null) {
                caffeineService.remove(CacheType.USER_BOARDS, USER_BOARD_KEY);
            }
        } catch (NullPointerException ignored) {
        } finally {
            BoardListResponse boards = boardService.listForUser();
            if (boards != null && !boards.getBoards().isEmpty()) {
                caffeineService.put(
                        CacheType.USER_BOARDS,
                        USER_BOARD_KEY,
                        boards
                );
            }
        }
    }

    @Around(value = "execution(* com..BoardService.listForUser(..))",
            argNames = "point")
    public BoardListResponse getBoardsInCached(ProceedingJoinPoint point) throws Throwable{
        try {
            BoardListResponse cachedData = caffeineService.get(
                    CacheType.USER_BOARDS,
                    USER_BOARD_KEY
            );
            if (cachedData != null)
                return cachedData;
        } catch (NullPointerException ignore) {
        }

        BoardListResponse result = (BoardListResponse) point.proceed();

        caffeineService.put(
                CacheType.USER_BOARDS,
                USER_BOARD_KEY,
                result)
        ;
        return result;
    }


}

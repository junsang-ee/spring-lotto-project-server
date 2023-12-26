package com.lotto.web.controller;

import com.lotto.web.model.dto.response.BoardListResponse;
import com.lotto.web.model.dto.response.common.ApiSuccessResponse;
import com.lotto.web.model.entity.PostEntity;
import com.lotto.web.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/api/board")
@RestController
public class BoardController extends BaseController {

    private final BoardService boardService;

    @GetMapping
    public ApiSuccessResponse<BoardListResponse> list() {
        return wrap(boardService.listForUser());
    }

    @GetMapping("/{boardId}/post")
    public ApiSuccessResponse<List<PostEntity>> postList(@PathVariable String boardId) {
        return null;
    }

}

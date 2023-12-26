package com.lotto.web.controller.admin;


import com.lotto.web.controller.BaseController;
import com.lotto.web.model.dto.request.BoardSaveRequest;
import com.lotto.web.model.dto.response.BoardSaveResponse;
import com.lotto.web.model.dto.response.common.ApiSuccessResponse;
import com.lotto.web.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/api/admin")
@RestController
public class AdminController extends BaseController {

    private final BoardService boardService;

    @PostMapping("/board")
    public ApiSuccessResponse<BoardSaveResponse> saveBoard(@RequestBody BoardSaveRequest request) {
        return wrap(boardService.save(request));
    }

    @DeleteMapping("/board/{boardId}")
    public ApiSuccessResponse<Boolean> deleteBoard(@PathVariable String boardId) {
        return wrap(boardService.deleteBoard(boardId));
    }

}

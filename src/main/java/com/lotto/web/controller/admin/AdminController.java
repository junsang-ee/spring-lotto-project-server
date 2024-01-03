package com.lotto.web.controller.admin;


import com.lotto.web.controller.BaseController;
import com.lotto.web.model.dto.request.BoardSaveRequest;
import com.lotto.web.model.dto.response.BoardListResponse;
import com.lotto.web.model.dto.response.common.ApiSuccessResponse;
import com.lotto.web.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/api/admin")
@RestController
public class AdminController extends BaseController {

    private final BoardService boardService;

    @PostMapping("/board")
    public ApiSuccessResponse<Boolean> saveBoard(@AuthenticationPrincipal(expression = "id") String userId,
                                                 @RequestBody BoardSaveRequest request) {
        return wrap(boardService.save(userId, request));
    }

    @GetMapping("/board")
    public ApiSuccessResponse<BoardListResponse> boardList() {
        return wrap(boardService.listAll());
    }

    @DeleteMapping("/board/{boardId}")
    public ApiSuccessResponse<Boolean> deleteBoard(@PathVariable String boardId) {
        return wrap(boardService.deleteBoard(boardId));
    }

}

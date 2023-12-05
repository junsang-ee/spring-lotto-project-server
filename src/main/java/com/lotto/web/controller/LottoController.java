package com.lotto.web.controller;

import com.lotto.web.model.dto.response.RandomLottoListResponse;
import com.lotto.web.model.dto.response.LottoWinningNumbersResponse;
import com.lotto.web.model.dto.response.common.ApiSuccessResponse;
import com.lotto.web.model.entity.LottoHistoryEntity;
import com.lotto.web.service.LottoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/api/lotto")
@RestController
public class LottoController extends BaseController {

    private final LottoService lottoService;

    @GetMapping("/random-list")
    public ApiSuccessResponse<RandomLottoListResponse> randomList(@RequestParam(defaultValue = "5000") int price,
                                                                  @RequestParam(required = false) List<Integer> exceptList,
                                                                  @RequestParam(required = false) List<Integer> needsList) {
        return wrap(lottoService.getRandomList(price, exceptList, needsList));
    }

    /* 당첨 회차로 번호 찾기 */
    @GetMapping("/winning/{round}")
    public ApiSuccessResponse<LottoWinningNumbersResponse> winningNumbers(@PathVariable int round) {
        return wrap(lottoService.getWinningNumbersByRound(round));
    }

    /* 당첨 날짜로 번호 찾기 */
    @GetMapping("/winning/{date}")
    public ApiSuccessResponse<LottoWinningNumbersResponse> winningNumbers(@PathVariable Date roundDate) {
        return wrap(lottoService.getWinningNumbersByDrawDate(roundDate));
    }

    /* 모든 당첨 번호 가져오기 */
    @GetMapping("/winning/list")
    public ApiSuccessResponse<List<LottoHistoryEntity>> allWinningNumbers() {
        return wrap(lottoService.getAllWinningNumbers());
    }
}

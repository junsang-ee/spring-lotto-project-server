package com.lotto.web.controller;

import com.lotto.web.model.dto.request.LottoListRequest;
import com.lotto.web.model.dto.response.DefaultLottoListResponse;
import com.lotto.web.model.dto.response.LottoWinningNumbersResponse;
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
public class LottoController {

    private final LottoService lottoService;

    @GetMapping("/random-list")
    public DefaultLottoListResponse randomList(@RequestParam(defaultValue = "5000") int price,
                                               @RequestParam(required = false) List<Integer> exceptList,
                                               @RequestParam(required = false) List<Integer> needsList) {
        return lottoService.getRandomList(price, exceptList, needsList);
    }

    /* 당첨 회차로 번호 찾기 */
    @GetMapping("/winning/{round}")
    public LottoWinningNumbersResponse winningNumbers(@PathVariable int round) {
        return lottoService.getWinningNumbersByRound(round);
    }

    /* 당첨 날짜로 번호 찾기 */
    @GetMapping("/winning/{date}")
    public LottoWinningNumbersResponse winningNumbers(@PathVariable Date roundDate) {
        return lottoService.getWinningNumbersByDrawDate(roundDate);
    }

    /* 모든 당첨 번호 가져오기 */
    @GetMapping("/winning/list")
    public List<LottoHistoryEntity> allWinningNumbers() {
        return lottoService.getAllWinningNumbers();
    }
}

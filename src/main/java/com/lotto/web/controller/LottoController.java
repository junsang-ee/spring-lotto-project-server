package com.lotto.web.controller;

import com.lotto.web.model.dto.request.LottoListRequest;
import com.lotto.web.model.dto.response.DefaultLottoListResponse;
import com.lotto.web.model.dto.response.LottoWinningNumbersResponse;
import com.lotto.web.service.LottoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RequiredArgsConstructor
@RequestMapping("/api/lotto")
@RestController
public class LottoController {

    private final LottoService lottoService;

    @GetMapping("/random-list")
    public DefaultLottoListResponse randomList(@RequestBody LottoListRequest request) {
        return lottoService.getRandomList(request);
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
}

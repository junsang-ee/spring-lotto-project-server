package com.lotto.web.controller;

import com.lotto.web.model.dto.request.LottoListGetRequest;
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

    @GetMapping("/excluded/list")
    public DefaultLottoListResponse getRandomList(@RequestBody LottoListGetRequest request) {
        return lottoService.getRandomList(request);
    }

    /* 당첨 회차로 번호 찾기 */
    @GetMapping("/{round}")
    public LottoWinningNumbersResponse getWinningNumbers(@PathVariable int round) {
        return null;
    }

    /* 당첨 날짜로 번호 찾기 */
    @GetMapping("/{date}")
    public LottoWinningNumbersResponse getWinningNumbers(@PathVariable Date roundDate) {
        return null;
    }
}

package com.lotto.web.controller;

import com.lotto.web.model.dto.response.ExtractionDetailResponse;
import com.lotto.web.model.dto.response.RandomLottoListResponse;
import com.lotto.web.model.dto.response.LottoWinningNumbersResponse;
import com.lotto.web.model.dto.response.common.ApiSuccessResponse;
import com.lotto.web.model.dto.response.common.PageResponse;
import com.lotto.web.model.entity.lotto.LottoWinningHistoryEntity;
import com.lotto.web.service.LottoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/lotto")
@RestController
public class LottoController extends BaseController {

    private final LottoService lottoService;

    @GetMapping("/randoms")
    public ApiSuccessResponse<RandomLottoListResponse> randoms(@AuthenticationPrincipal(expression = "id") String userId,
                                                                  @RequestParam(defaultValue = "5000") int price,
                                                                  @RequestParam(required = false) List<Integer> exceptList,
                                                                  @RequestParam(required = false) List<Integer> needsList) {
        return wrap(lottoService.getRandomList(userId, price, exceptList, needsList));
    }

    /* 당첨 회차로 번호 찾기 */
    @GetMapping("/winning/{round}")
    public ApiSuccessResponse<LottoWinningNumbersResponse> winning(@PathVariable int round) {
        return wrap(lottoService.getWinningNumbersByRound(round));
    }

    /* 당첨 날짜로 번호 찾기 */
    @GetMapping("/winning/{date}")
    public ApiSuccessResponse<LottoWinningNumbersResponse> winning(@PathVariable Date roundDate) {
        return wrap(lottoService.getWinningNumbersByDrawDate(roundDate));
    }

    /* 모든 당첨 번호 가져오기 */
    @GetMapping("/winnings")
    public ApiSuccessResponse<List<LottoWinningHistoryEntity>> winnings() {
        return wrap(lottoService.getAllWinningNumbers());
    }

    /* 모든 추출 번호 가져오기 */
    @GetMapping("/extractions")
    public ApiSuccessResponse<PageResponse<ExtractionDetailResponse>> extractions(@AuthenticationPrincipal(expression = "id") String userId,
                                                                                        Pageable pageable) {
        return page(lottoService.getAllExtractions(userId, pageable));
    }
}

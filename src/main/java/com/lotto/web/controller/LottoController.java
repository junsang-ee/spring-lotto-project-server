package com.lotto.web.controller;

import com.lotto.web.model.dto.request.LottoListGetRequest;
import com.lotto.web.model.dto.response.LottoListGetResponse;
import com.lotto.web.service.LottoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/lotto")
@RestController
public class LottoController {

    private final LottoService lottoService;

    @GetMapping("/excluded/list")
    public LottoListGetResponse list(@RequestBody LottoListGetRequest request) {
        return lottoService.list(request);
    }

}

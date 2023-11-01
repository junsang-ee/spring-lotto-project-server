package com.lotto.web.controller;

import com.lotto.web.model.dto.request.LottoExceptListGetRequest;
import com.lotto.web.model.dto.response.LottoGetResponse;
import com.lotto.web.model.dto.response.LottoListGetResponse;
import com.lotto.web.service.LottoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/lotto")
@RestController
public class LottoController {

    private final LottoService lottoService;

    @GetMapping
    public LottoGetResponse get() {
        return lottoService.get();
    }

    @GetMapping("/list")
    public LottoListGetResponse list(@RequestBody int price) {
        return lottoService.list(price);
    }

    @GetMapping("/excluded/list")
    public LottoListGetResponse excludedList(@RequestBody LottoExceptListGetRequest request) {
        return null;
    }

}

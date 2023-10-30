package com.lotto.web.controller;

import com.lotto.web.model.dto.response.LottoResponse;
import com.lotto.web.model.vo.LottoVO;
import com.lotto.web.service.LottoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/lotto")
@RestController
public class LottoController {

    private final LottoService lottoService;

    @GetMapping
    public LottoResponse get() {
        return lottoService.get();
    }
}

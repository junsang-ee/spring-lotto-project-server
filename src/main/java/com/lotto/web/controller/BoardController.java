package com.lotto.web.controller;

import com.lotto.web.config.BoardActivation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@RequestMapping("/api/board")
@RestController
public class BoardController extends BaseController{
    private String name;

    @Enumerated(EnumType.STRING)
    private BoardActivation activation;

}

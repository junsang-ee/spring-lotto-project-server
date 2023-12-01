package com.lotto.web.controller;

import com.lotto.web.model.entity.UserEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RequestMapping("/api/auth")
@RestController
public class AuthController {

    @GetMapping("/me")
    public UserEntity me() {
        return null;
    }
}

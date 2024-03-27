package com.lotto.web.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncryptUtil {
    private static final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public static String encode(String rawPassword) {
        return bCryptPasswordEncoder.encode(rawPassword);
    }

}

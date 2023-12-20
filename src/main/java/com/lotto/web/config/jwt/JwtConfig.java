package com.lotto.web.config.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Base64;

@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "junsang.jwt")
@Component
public class JwtConfig {
    private String secret;

    private Duration validTime;

    public String getEncodedSecret() {
        return Base64.getEncoder().encodeToString(getSecret().getBytes());
    }
}

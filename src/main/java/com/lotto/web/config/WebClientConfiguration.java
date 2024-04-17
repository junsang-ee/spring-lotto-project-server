package com.lotto.web.config;

import io.netty.channel.ChannelOption;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfiguration {

    private static final String LOTTO_API_URI = "https://www.dhlottery.co.kr";
    HttpClient httpClient = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000);

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(LOTTO_API_URI)
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(2 * 1024 * 1024))
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}

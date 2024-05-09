package com.lotto.web.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.netty.channel.ChannelOption;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.ClientCodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import static org.springframework.http.MediaType.TEXT_HTML;

@Configuration
public class WebClientConfiguration {

    public final ObjectMapper objectMapper =
            new ObjectMapper().configure(
                    DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                    false
            ).registerModule(new JavaTimeModule());

    HttpClient httpClient = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000);

    @Bean
    public WebClient webClient() {
        return WebClient.builder().codecs(ClientCodecConfigurer::defaultCodecs)
                .exchangeStrategies(defaultExchangeStrategies())
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    @Bean
    public ExchangeStrategies defaultExchangeStrategies() {
        return ExchangeStrategies.builder().codecs(
                config -> {
                    config.defaultCodecs().jackson2JsonEncoder(
                            new Jackson2JsonEncoder(
                                    objectMapper,
                                    TEXT_HTML
                            )
                    );
                    config.defaultCodecs().jackson2JsonDecoder(
                            new Jackson2JsonDecoder(
                                    objectMapper,
                                    TEXT_HTML
                            )
                    );
                    config.defaultCodecs().maxInMemorySize(1024 * 1024);
                }).build();
    }
}

package com.lotto.web.util;

import com.lotto.web.constants.messages.ErrorMessage;
import com.lotto.web.exception.custom.InvalidStateException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebClientUtil {
    private final WebClient webClient;

    public <T> Mono<T> get(String uri, Class<T> response) {
        return webClient.method(HttpMethod.GET)
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(
                        HttpStatus::is4xxClientError,
                        clientResponse -> Mono.error(
                                        new InvalidStateException(ErrorMessage.BAD_REQUEST)
                        )
                )
                .onStatus(
                        HttpStatus::is5xxServerError,
                        clientResponse -> Mono.error(
                                new InvalidStateException(ErrorMessage.INTERNAL_SERVER_ERROR)
                        )
                )
                .bodyToMono(response)
                .doOnError(e -> log.error("Error occurred during WebClient call", e));
    }
}

package com.lotto.web.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lotto.web.config.WebClientConfiguration;
import com.lotto.web.constants.messages.ErrorMessage;
import com.lotto.web.exception.custom.InvalidStateException;

import com.lotto.web.model.entity.lotto.LottoWinningHistoryEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebClientUtil {
    private final WebClientConfiguration webClientConfiguration;

    public LottoWinningHistoryEntity get(int round) {
        return webClientConfiguration.webClient().method(HttpMethod.GET)
                .uri(uriBuilder -> uriBuilder.path("/common.do")
                        .queryParam("method", "getLottoNumber")
                        .queryParam("drwNo", round)
                        .build())
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
                .bodyToMono(String.class)
                .map(this::convert)
                .doOnError(e -> log.error("Error occurred during WebClient call", e))
                .block();
    }

    private LottoWinningHistoryEntity convert(String responseBody) {
        try {
            JsonNode jsonNode = new ObjectMapper().readTree(responseBody);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date drawDate = formatter.parse(jsonNode.get("drwNoDate").asText());
            return LottoWinningHistoryEntity.of(
                    jsonNode.get("drwtNo1").asInt(),
                    jsonNode.get("drwtNo2").asInt(),
                    jsonNode.get("drwtNo3").asInt(),
                    jsonNode.get("drwtNo4").asInt(),
                    jsonNode.get("drwtNo5").asInt(),
                    jsonNode.get("drwtNo6").asInt(),
                    jsonNode.get("bnusNo").asInt(),
                    jsonNode.get("drwNo").asInt(),
                    drawDate
            );
        } catch(IOException e) {
            log.error("Error convert to LottoWinningHistoryEntity :: ", e);

        } catch (ParseException e) {
            log.error("Error Parsing Date :: ", e);
        }
        return null;
    }
}

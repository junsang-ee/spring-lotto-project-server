package com.lotto.web.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lotto.web.model.dto.api.LottoApiResponse;

import io.netty.channel.ChannelOption;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.ClientCodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.netty.http.client.HttpClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.TEXT_HTML;

@Slf4j
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class WebClientUtilTest {

    static final String LOTTO_URI = "/common.do?method=getLottoNumber&drwNo=";

    final ObjectMapper objectMapper = new ObjectMapper().configure(
            DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
            false
    ).registerModule(new JavaTimeModule());

    HttpClient httpClient = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000);

    WebClient webClient;

    @BeforeEach
    void setUp() {
        webClient = WebClient.builder().codecs(ClientCodecConfigurer::defaultCodecs)
                .baseUrl("https://www.dhlottery.co.kr")
                .exchangeStrategies(defaultExchangeStrategies())
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    @Test
    @DisplayName("WebClient - Lotto API 응답 데이터 비교 테스트")
    void lottoApiWebClientTest() {

        // given
        LottoApiResponse temp = new LottoApiResponse();
        setLottoResponse(temp);

        // when
        LottoApiResponse result = webClient.get()
                .uri(LOTTO_URI + 1000)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(LottoApiResponse.class)
                .block();

        // then
        assert result != null;
        assertThat(temp.getDrwtNo1()).isEqualTo(result.getDrwtNo1());
        assertThat(temp.getDrwtNo2()).isEqualTo(result.getDrwtNo2());
        assertThat(temp.getDrwtNo3()).isEqualTo(result.getDrwtNo3());
        assertThat(temp.getDrwtNo4()).isEqualTo(result.getDrwtNo4());
        assertThat(temp.getDrwtNo5()).isEqualTo(result.getDrwtNo5());
        assertThat(temp.getDrwtNo6()).isEqualTo(result.getDrwtNo6());
        assertThat(temp.getBnusNo()).isEqualTo(result.getBnusNo());
        assertThat(temp.getDrwNo()).isEqualTo(result.getDrwNo());
    }

    void setLottoResponse(LottoApiResponse response) {
        response.setDrwtNo1(2);
        response.setDrwtNo2(8);
        response.setDrwtNo3(19);
        response.setDrwtNo4(22);
        response.setDrwtNo5(32);
        response.setDrwtNo6(42);
        response.setDrwNo(1000);
        response.setBnusNo(39);
    }

    ExchangeStrategies defaultExchangeStrategies() {
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

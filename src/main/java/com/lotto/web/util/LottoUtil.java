package com.lotto.web.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

@Slf4j
@RequiredArgsConstructor
public class LottoUtil {
    public static final String LOTTO_API_URI = "https://www.dhlottery.co.kr/";
    public static final int priceUnit = 1000;

    public static boolean getIsCorrectPriceUnit(int price) {
        return Math.floorMod(price, priceUnit) == 0;
    }

    public static int getLottoCount(int price) {
        return Math.floorDiv(price, priceUnit);
    }

    public static int getRandomNumber() {
        return (int) (Math.random() * 45 + 1);
    }

    public static int getExtractionMatchingRound() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime drawDateTime = calculateDrawDateTime(now);
        if (now.isAfter(drawDateTime))
            return calculateDrawNumber(drawDateTime);
        return calculateDrawNumber(drawDateTime.minusWeeks(1));
    }

    public static String getLottoApiUri(int round) {
        String queryParams = LOTTO_API_URI + "common.do?method=getLottoNumber&drwNo=";
        return queryParams + round;
    }

    private static LocalDateTime calculateDrawDateTime(LocalDateTime dateTime) {
        LocalDateTime saturday = dateTime.with(
                TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY)
        );
        return LocalDateTime.of(
                saturday.toLocalDate(),
                LocalTime.of(
                        LottoDefault.HOUR,
                        LottoDefault.MINUTE
                )
        );
    }

    private static int calculateDrawNumber(LocalDateTime drawDateTime) {
        LocalDate startDate = LocalDate.of(
                LottoDefault.YEAR,
                LottoDefault.MONTH,
                LottoDefault.DAY
        );
        long weeks = ChronoUnit.WEEKS.between(
                startDate,
                drawDateTime.toLocalDate()
        );
        return (int) weeks + 2;
    }

    protected static class LottoDefault {
        private static final int YEAR = 2002;
        private static final int MONTH = 12;
        private static final int DAY = 7;
        private static final int HOUR = 20;
        private static final int MINUTE = 34;
    }
}

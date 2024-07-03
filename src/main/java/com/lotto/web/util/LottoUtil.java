package com.lotto.web.util;

import com.lotto.web.constants.MatchStatus;
import com.lotto.web.model.entity.lotto.ExtractionHistoryEntity;
import com.lotto.web.model.entity.lotto.LottoWinningHistoryEntity;
import com.lotto.web.model.entity.lotto.WinningStatusEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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

    public static int getExtractionMatchingRound(Instant createdAt) {
        LocalDateTime extractionAt = LocalDateTime.ofInstant(createdAt, Constants.ZONE_KR);
        LocalDateTime drawAt = calculateDrawDateTime(extractionAt);
        if (extractionAt.isAfter(drawAt))
            return calculateDrawNumber(drawAt);
        return calculateDrawNumber(drawAt.minusWeeks(1));
    }

    public static String getLottoApiUri(int round) {
        String queryParams = LOTTO_API_URI + "common.do?method=getLottoNumber&drwNo=";
        return queryParams + round;
    }

    public static void checkMatchingExtraction(ExtractionHistoryEntity extractionHistory,
                                               LottoWinningHistoryEntity lottoWinningHistory) {
        List<Integer> extractionList = extractionToList(extractionHistory);
        List<Integer> winningList = winningToList(lottoWinningHistory);
        WinningStatusEntity winningStatus = extractionHistory.getWinningStatus();
        AtomicInteger matchCount = new AtomicInteger();

        IntStream.range(0, extractionList.size()).forEach(
                index -> {
                    MatchStatus matchStatus = winningList.contains(extractionList.get(index)) ?
                            MatchStatus.MATCH : MatchStatus.NOT_MATCH;
                    winningStatus.updateStatus(index, matchStatus);
                    if (matchStatus == MatchStatus.MATCH) matchCount.getAndIncrement();
                }
        );

        boolean isMatchBonus = false;

        if (matchCount.get() == 5) {
            isMatchBonus = extractionList.contains(
                    lottoWinningHistory.getBonusNumber()
            );
        }

        winningStatus.updateWinningResult(
                matchCount.get(), isMatchBonus
        );
    }

    private static List<Integer> extractionToList(ExtractionHistoryEntity entity) {
        return Stream.of(
                entity.getFirstNumber(),
                entity.getSecondNumber(),
                entity.getThirdNumber(),
                entity.getFourthNumber(),
                entity.getFifthNumber(),
                entity.getSixthNumber()
        ).collect(Collectors.toList());

    }

    private static List<Integer> winningToList(LottoWinningHistoryEntity entity) {
        return Stream.of(
                entity.getFirstNumber(),
                entity.getSecondNumber(),
                entity.getThirdNumber(),
                entity.getFourthNumber(),
                entity.getFifthNumber(),
                entity.getSixthNumber()
        ).collect(Collectors.toList());
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

package com.lotto.web.service.crawler;

import com.lotto.web.model.entity.lotto.LottoWinningHistoryEntity;
import com.lotto.web.repository.LottoWinningHistoryRepository;
import com.lotto.web.util.LottoUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CrawlerService {

    private final LottoWinningHistoryRepository lottoWinningHistoryRepository;

    @Transactional
    public void createWinningHistory() {
        LottoWinningHistoryEntity winnings = new LottoWinningHistoryEntity();
        try {
            Document document =
                    Jsoup.connect("https://search.naver.com/search.naver?query=로또3회차").get();
            Elements divDate = document.select(LottoUtil.DIV_DATE);
            Elements divWinnings = document.select(LottoUtil.DIV_WINNINGS);
            Elements divBonus = document.select(LottoUtil.DIV_BONUS);
            String[] winningArr = divWinnings.get(0).text().split("\\s+");
            List<Integer> winningList = Arrays.stream(winningArr).map(
                    Integer::parseInt
            ).collect(Collectors.toList());
            Element bonusElement = divBonus.get(0);
            Element dateElement = divDate.get(0);
            winnings.setFirstNumber(winningList.get(0));
            winnings.setSecondNumber(winningList.get(1));
            winnings.setThirdNumber(winningList.get(2));
            winnings.setFourthNumber(winningList.get(3));
            winnings.setFifthNumber(winningList.get(4));
            winnings.setSixthNumber(winningList.get(5));
            winnings.setBonusNumber(Integer.parseInt(bonusElement.text()));
            String dateStr = dateElement.text().substring(
                    dateElement.text().indexOf("(")+1,
                    dateElement.text().indexOf(")")-1
            );
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
            Date date = formatter.parse(dateStr);
            winnings.setRound(1);
            winnings.setDrawDate(date);
            lottoWinningHistoryRepository.save(winnings);

        } catch (Exception ignored) {
        }

    }
}

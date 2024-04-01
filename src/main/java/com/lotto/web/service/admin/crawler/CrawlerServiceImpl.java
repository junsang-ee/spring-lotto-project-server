package com.lotto.web.service.admin.crawler;

import com.lotto.web.repository.LottoWinningHistoryRepository;
import lombok.RequiredArgsConstructor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class CrawlerServiceImpl implements CrawlerService {

    private final LottoWinningHistoryRepository lottoWinningHistoryRepository;

    @Override
    public Document getLottoDocumentByRound(String round) {
        Document document = null;
        String query = "query=로또" + round + "회차";
        try {
            document = Jsoup
                    .connect("https://search.naver.com/search.naver?" + query)
                    .get();
        } catch (IOException ignored){
        }
        return document;
    }
}

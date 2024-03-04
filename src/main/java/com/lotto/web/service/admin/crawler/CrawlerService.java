package com.lotto.web.service.admin.crawler;

import org.jsoup.nodes.Document;
public interface CrawlerService {
    Document getLottoDocumentByRound(String round);

}

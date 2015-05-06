package com.crossover.trial.service.impl;

import com.crossover.trial.dto.Transaction;
import com.crossover.trial.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by anirudh on 04/05/15.
 */

@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String INDEX_URL = "/api/search/index";

    @Value("${search.api.base.url}")
    protected String searchBaseUrl;


    @Override
    public void indexDocument(Transaction transactionDto) {
        final String url = searchBaseUrl + INDEX_URL + "?id=123" + "&accountNumber=" + transactionDto.getAccountNumber() +
                "&description=" + transactionDto.getDescription() + "&closingBalance=" + transactionDto.getClosingBalance() +
                "&transactionType=" + "DEPOSIT&amount=" + transactionDto.getAmount();
        System.out.println("url : " + url);
        restTemplate.getForObject(url, String.class);
    }
}

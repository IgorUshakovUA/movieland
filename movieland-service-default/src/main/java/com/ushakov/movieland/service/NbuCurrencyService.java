package com.ushakov.movieland.service;

import com.ushakov.movieland.common.Currency;
import com.ushakov.movieland.entity.NbuCurrency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NbuCurrencyService implements CurrencyService {
    private static final ParameterizedTypeReference<List<NbuCurrency>> PARAMETERIZED_TYPE_REFERENCE = new ParameterizedTypeReference<List<NbuCurrency>>() {
    };

    @Value("${nbu.rest.url}")
    private String restUrl;

    private RestTemplate restTemplate;

    private volatile Map<String, Double> currencies;

    @Autowired
    public NbuCurrencyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public double getCurrencyRate(Currency currency) {
        return currencies.get(currency.value());
    }

    @Scheduled(cron = "${nbu.rest.cron}")
    @PostConstruct
    public void reloadCurrencies() {
        try {
            ResponseEntity<List<NbuCurrency>> responseEntity = restTemplate.exchange(restUrl, HttpMethod.GET, null, PARAMETERIZED_TYPE_REFERENCE);
            List<NbuCurrency> currencyList = responseEntity.getBody();
            Map<String, Double> newCurrencies = new HashMap<>();
            for (NbuCurrency nbuCurrency : currencyList) {
                newCurrencies.put(nbuCurrency.getName(), nbuCurrency.getRate());
            }
            currencies = newCurrencies;
        }
        catch (RestClientException e) {
            if(currencies == null) {
                currencies = new HashMap<>();
            }
        }
    }
}

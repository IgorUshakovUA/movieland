package com.ushakov.movieland.service;

import com.ushakov.movieland.common.Currency;
import com.ushakov.movieland.entity.NbuCurrency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class DefaultCurrencyService implements CurrencyService {
    private static final String REST_URL = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?valcode=";
    private static final ParameterizedTypeReference<List<NbuCurrency>> PARAMETERIZED_TYPE_REFERENCE = new ParameterizedTypeReference<List<NbuCurrency>>() {
    };
    LocalDate lastUpdated;

    private RestTemplate restTemplate;

    private NbuCurrency usdCurrency;
    private NbuCurrency eurCurrency;

    @Autowired
    public DefaultCurrencyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public double getCurrencyRate(Currency currency) {
        if (currency == null) {
            return 1;
        }

        if (lastUpdated == null || lastUpdated.getDayOfMonth() != LocalDate.now().getDayOfMonth()) {
            lastUpdated = LocalDate.now();
            usdCurrency = getCurrency(Currency.USD);
            eurCurrency = getCurrency(Currency.EUR);
        }

        if (currency == Currency.EUR) {
            return eurCurrency.getRate();
        } else {
            return usdCurrency.getRate();
        }
    }

    private NbuCurrency getCurrency(Currency currency) {
        String eurURL = buildCurrencyUrl(currency);
        ResponseEntity<List<NbuCurrency>> responseEntity = restTemplate.exchange(eurURL, HttpMethod.GET, null, PARAMETERIZED_TYPE_REFERENCE);
        List<NbuCurrency> currencyList = responseEntity.getBody();
        return currencyList.get(0);
    }

    public static String buildCurrencyUrl(Currency currency) {
        if (currency == null) {
            return null;
        }

        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        StringBuilder result = new StringBuilder(REST_URL);

        if (currency == Currency.EUR) {
            result.append("EUR&date=");
        } else {
            result.append("USD&date=");
        }

        result.append(localDate.format(formatter));

        result.append("&json");

        return result.toString();
    }
}

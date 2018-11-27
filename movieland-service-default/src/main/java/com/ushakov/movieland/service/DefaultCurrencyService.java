package com.ushakov.movieland.service;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ushakov.movieland.common.Currency;
import com.ushakov.movieland.entity.NbuCurrency;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

@Service
public class DefaultCurrencyService implements CurrencyService {
    @Override
    public double getCurrencyRate(Currency currency) {
        if (currency == null) {
            return 1;
        }

        URI uri;
        try {
            uri = new URI(buildCurrencyUrl(currency));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(uri);
        CloseableHttpResponse response;

        try {
            response = httpclient.execute(httpGet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        HttpEntity entity = response.getEntity();
        String result;

        try {
            result = EntityUtils.toString(entity);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        result = (result.replace("[", "")).replace("]", "");

        ObjectMapper mapper = new ObjectMapper();

        try {
            NbuCurrency nbuCurrency = mapper.readValue(result, NbuCurrency.class);

            return nbuCurrency.getRate();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String buildCurrencyUrl(Currency currency) {
        if (currency == null) {
            return null;
        }

        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        StringBuilder result = new StringBuilder();

        if (currency == Currency.EUR) {
            result.append("https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?valcode=EUR&date=");
        } else {
            result.append("https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?valcode=USD&date=");
        }

        result.append(localDate.format(formatter));

        result.append("&json");

        return result.toString();
    }
}

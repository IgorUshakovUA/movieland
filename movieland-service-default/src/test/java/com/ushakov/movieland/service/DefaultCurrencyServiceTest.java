package com.ushakov.movieland.service;

import com.ushakov.movieland.common.Currency;
import com.ushakov.movieland.entity.NbuCurrency;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.AdditionalMatchers.eq;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DefaultCurrencyServiceTest {

    @Test
    public void testGetCurrencyRate() {
        // Prepare
        RestTemplate restTemplate = mock(RestTemplate.class);

        CurrencyService currencyService = new DefaultCurrencyService(restTemplate);

        List<NbuCurrency> currencyList = new ArrayList<>();
        NbuCurrency currency = new NbuCurrency();
        currency.setName("USD");
        currency.setRate(28.9);
        currencyList.add(currency);

        ResponseEntity<List<NbuCurrency>> responseEntity = new ResponseEntity<>(currencyList,HttpStatus.OK);
        ParameterizedTypeReference<List<NbuCurrency>> parameterizedTypeReference = new ParameterizedTypeReference<List<NbuCurrency>>() {
        };

        // When
        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(HttpEntity.class), any(parameterizedTypeReference.getClass()))).thenReturn(responseEntity);

        double rateUSD = currencyService.getCurrencyRate(Currency.USD);

        assertEquals(28.9, rateUSD, 1e-3);

        rateUSD = currencyService.getCurrencyRate(Currency.USD);

        assertEquals(28.9, rateUSD, 1e-3);
    }

    @Test
    public void testBuildCurrencyUrl() {
        String urlEUR = DefaultCurrencyService.buildCurrencyUrl(Currency.EUR);
        String urlUSD = DefaultCurrencyService.buildCurrencyUrl(Currency.USD);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        LocalDate dateEUR = LocalDate.parse(urlEUR.substring(78, 86), formatter);

        LocalDate dateUSD = LocalDate.parse(urlUSD.substring(78, 86), formatter);

        assertNull(DefaultCurrencyService.buildCurrencyUrl(null));

        assertTrue(urlEUR.indexOf("&json") > -1);
        assertEquals("https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?valcode=EUR&date=", urlEUR.substring(0, 78));
        assertEquals(LocalDate.now(), dateEUR);

        assertTrue(urlUSD.indexOf("&json") > -1);
        assertEquals("https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?valcode=USD&date=", urlUSD.substring(0, 78));
        assertEquals(LocalDate.now(), dateUSD);
    }

}
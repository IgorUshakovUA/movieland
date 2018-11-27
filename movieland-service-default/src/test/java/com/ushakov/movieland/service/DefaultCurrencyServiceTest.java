package com.ushakov.movieland.service;

import com.ushakov.movieland.common.Currency;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;

public class DefaultCurrencyServiceTest {

    @Test
    public void testGetCurrencyRate() {
        CurrencyService currencyService = new DefaultCurrencyService();

        double rateEUR = currencyService.getCurrencyRate(Currency.EUR);

        assertTrue(rateEUR > 1);

        double rateUSD = currencyService.getCurrencyRate(Currency.USD);

        assertTrue(rateUSD > 1);

        assertEquals(1, currencyService.getCurrencyRate(null), 1e-3);
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
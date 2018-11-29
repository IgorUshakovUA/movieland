package com.ushakov.movieland.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyEditorSupport;

public class CurrencyConverter extends PropertyEditorSupport {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void setAsText(String text) throws IllegalArgumentException {

        String capitalized = text.toUpperCase().trim();
        Currency currency;
        try {
            currency = Currency.valueOf(capitalized);
        } catch (IllegalArgumentException e) {
            logger.warn("Wrong value was provided: {}", text);

            currency = null;
        }

        setValue(currency);
    }
}

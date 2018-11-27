package com.ushakov.movieland.common;

import java.beans.PropertyEditorSupport;

public class CurrencyConverter extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {

        String capitalized = text.toUpperCase().trim();
        Currency currency;
        try {
            currency = Currency.valueOf(capitalized);
        } catch (IllegalArgumentException e) {
            currency = null;
        }

        setValue(currency);
    }
}

package com.ushakov.movieland.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyEditorSupport;

public class SortFieldConverter extends PropertyEditorSupport {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void setAsText(String text) throws IllegalArgumentException {

        String capitalized = text.toUpperCase().trim();
        SortField sortField;
        try {
            sortField = SortField.valueOf(capitalized);
        } catch (IllegalArgumentException e) {
            logger.warn("Wrong value was provided: {}", text);

            sortField = null;
        }

        setValue(sortField);
    }
}

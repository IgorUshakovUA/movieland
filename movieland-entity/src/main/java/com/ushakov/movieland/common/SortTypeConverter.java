package com.ushakov.movieland.common;

import java.beans.PropertyEditorSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SortTypeConverter extends PropertyEditorSupport {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void setAsText(String text) throws IllegalArgumentException {

        String capitalized = text.toUpperCase().trim();
        SortType sortType;
        try {
            sortType = SortType.valueOf(capitalized);
        } catch (IllegalArgumentException e) {
            logger.warn("Wrong value was provided: {}", text);

            sortType = null;
        }

        setValue(sortType);
    }
}

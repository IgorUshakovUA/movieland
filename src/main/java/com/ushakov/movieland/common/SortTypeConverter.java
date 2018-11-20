package com.ushakov.movieland.common;

import java.beans.PropertyEditorSupport;

public class SortTypeConverter extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {

        String capitalized = text.toUpperCase().trim();
        SortType sortType;
        try {
            sortType = SortType.valueOf(capitalized);
        } catch (IllegalArgumentException e) {
            sortType = null;
        }

        setValue(sortType);
    }
}

package com.ushakov.movieland.common;

import java.beans.PropertyEditorSupport;

public class SortFieldConverter extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {

        String capitalized = text.toUpperCase().trim();
        SortField sortField;
        try {
            sortField = SortField.valueOf(capitalized);
        } catch (IllegalArgumentException e) {
            sortField = null;
        }

        setValue(sortField);
    }
}

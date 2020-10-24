package com.redfox.webapp.model;

public class DateTextContent implements Content {
    private final Content content;
    private final String date;

    public DateTextContent(Content content, String date) {
        this.content = content;
        this.date = date;
    }
}

package com.redfox.webapp.model;

public class BoldTextContent extends TextContent {
    private final String boldText;

    public BoldTextContent(String text) {
        super(text);
        this.boldText = text;
    }

    public BoldTextContent(String text, String boldText) {
        super(text);
        this.boldText = boldText;
    }
}

package com.redfox.webapp.model;

public class TextContent implements Content {
    protected String str;

    public TextContent(String str) {
        this.str = str;
    }

    @Override
    public String getText() {
        return str;
    }
}

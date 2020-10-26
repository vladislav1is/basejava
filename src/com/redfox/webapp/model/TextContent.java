package com.redfox.webapp.model;

public class TextContent implements Content {
    private final String content;

    public TextContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return content;
    }
}

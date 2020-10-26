package com.redfox.webapp.model;

import java.util.Objects;

public class TextContent implements Content {
    private final String text;

    public TextContent(String text) {
        Objects.requireNonNull(text, "text must not be null");
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}

package com.redfox.webapp.model;

import java.util.List;

public class ListTextContent implements Content {
    private final List<String> texts;

    public ListTextContent(List<String> texts) {
        this.texts = texts;
    }

    @Override
    public String toString() {
        return texts.toString();
    }
}

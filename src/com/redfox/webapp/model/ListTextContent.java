package com.redfox.webapp.model;

import java.util.List;
import java.util.Objects;

public class ListTextContent implements Content {
    private final List<String> texts;

    public ListTextContent(List<String> texts) {
        Objects.requireNonNull(texts, "texts must not be null");
        this.texts = texts;
    }

    @Override
    public String toString() {
        return texts.toString();
    }
}

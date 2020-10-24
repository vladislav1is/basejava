package com.redfox.webapp.model;

import java.util.List;

public class HeaderTextContent implements Content {
    private final String header;
    private final List<Content> texts;

    public HeaderTextContent(String header, List<Content> texts) {
        this.header = header;
        this.texts = texts;
    }
}

package com.redfox.webapp.model;

import java.util.List;
import java.util.Map;

public class MapTextContent implements Content {
    private Map<TextContent, ListTextContent> strings;

    public MapTextContent(Map<TextContent, ListTextContent> strings) {
        this.strings = strings;
    }

    @Override
    public String getText() {
        String text = "";
        for (Map.Entry<TextContent, ListTextContent> str : strings.entrySet()) {
            text += str.getKey().getText() + "\n";
            List<TextContent> paragraphs = str.getValue().getStrList();
            int size = paragraphs.size();
            for (int i = 0; i < size; i++) {
                text += paragraphs.get(i).getText() + "\n";
            }
        }
        return text;
    }
}

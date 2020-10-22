package com.redfox.webapp.model;

import java.util.List;

public class ListTextContent implements Content {
    private List<TextContent> strings;

    public ListTextContent(List<TextContent> strings) {
        this.strings = strings;
    }

    public List<TextContent> getStrList() {
        return strings;
    }

    public void setStrList(List<TextContent> strList) {
        this.strings = strList;
    }

    @Override
    public String getText() {
        String text = "";
        int size = strings.size();
        for (int i = 0; i < size-1; i++) {
            text += strings.get(i).getText() + "\n";
        }
        text += strings.get(size-1).getText();
        return text;
    }
}

package com.redfox.webapp.model;

public enum SectionType {
    PERSONAL("Личные качества") {
        @Override
        public String toHtml0(String value) {
            return "<b>" + value + "</b>";
        }
    },
    OBJECTIVE("Позиция"),
    ACHIEVEMENT("Достижения") {
        @Override
        public String toHtml0(String value) {
            return doUnorderedHtmlList(value);
        }
    },
    QUALIFICATIONS("Квалификация") {
        @Override
        public String toHtml0(String value) {
            return doUnorderedHtmlList(value);
        }
    },
    EXPERIENCE("Опыт работы"),
    EDUCATION("Образование");

    private String title;

    SectionType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String toHtml(String value) {
        return (value == null) ? "" : toHtml0(value);
    }

    protected String toHtml0(String value) {
        return value;
    }

    protected String doUnorderedHtmlList(String value) {
        String[] strs = value.substring(1, value.length() - 1).split(",");
        StringBuilder sb = new StringBuilder("<ul>");
        for (String s : strs) {
            sb.append("<li>" + s + "</li>");
        }
        return sb.append("</ul>").toString();
    }
}
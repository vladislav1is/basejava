package com.redfox.webapp.model;

public enum SectionType {
    PERSONAL("Личные качества"),
    OBJECTIVE("Позиция"),
    ACHIEVEMENT("Достижения"),
    QUALIFICATIONS("Квалификация"),
    EXPERIENCE("Опыт работы"),
    EDUCATION("Образование");

    private String title;
    private Content section;

    SectionType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setContent(Content content) {
        this.section = content;
    }

    public Content getSection() {
        return section;
    }
}

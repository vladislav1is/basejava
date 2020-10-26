package com.redfox.webapp.model;

public enum ContactType {
    PHONE("Тел"),
    SKYPE("Skype"),
    MAIL("Почта"),
    LINCKEDIN("Профиль LinkedIn"),
    GITHUB("Профиль Github"),
    STACKOVERFLOW("Профиль Stackoverflow"),
    HOMEPAGE("Домашняя страница");

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}

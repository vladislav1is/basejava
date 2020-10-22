package com.redfox.webapp.model;

public enum ContactType {
    PHONE("Телефон"),
    SKYPE("Skype"),
    MAIL("Почта");

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}

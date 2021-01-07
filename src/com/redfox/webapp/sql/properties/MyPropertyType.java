package com.redfox.webapp.sql.properties;

public enum MyPropertyType {
    STORAGE_DIR("storage.dir"),
    DB_URL("db.url"),
    DB_USER("db.user"),
    DB_PASSWORD("db.password");

    private String title;

    MyPropertyType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}

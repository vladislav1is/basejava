package com.redfox.webapp;

import com.redfox.webapp.sql.properties.DBConfiguration;
import com.redfox.webapp.sql.properties.PropertyType;

public class MainPropertiesTestData {
    public static void main(String[] args) {
        String path = "config/resumes12.properties";

        DBConfiguration config = new DBConfiguration(path);
        config.setDirectory("config/resumes12.properties");
        config.writeProperty(PropertyType.DB_URL, "jdbc:postgresql://localhost:5432/resumes");
        config.writeProperty(PropertyType.DB_USER, "postgres");
        config.writeProperty(PropertyType.DB_PASSWORD, "postgres");

        System.out.println(config.getProperty(PropertyType.STORAGE_DIR));
        System.out.println(config.getProperty(PropertyType.DB_URL));
        System.out.println(config.getProperty(PropertyType.DB_USER));
        System.out.println(config.getProperty(PropertyType.DB_PASSWORD));
    }
}

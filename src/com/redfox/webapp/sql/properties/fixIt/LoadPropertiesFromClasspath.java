package com.redfox.webapp.sql.properties.fixIt;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LoadPropertiesFromClasspath {
    public static void main(String[] args) {
        try (InputStream input = LoadPropertiesFromClasspath.class.getClassLoader().getResourceAsStream("resumes.properties")) {
            Properties prop = new Properties();

            if (input == null) {
                System.out.println("Sorry, unable to find resumes.properties");
                return;
            }

            prop.load(input);

            System.out.println(prop.getProperty("storage.dir"));
            System.out.println(prop.getProperty("db.url"));
            System.out.println(prop.getProperty("db.user"));
            System.out.println(prop.getProperty("db.password"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}
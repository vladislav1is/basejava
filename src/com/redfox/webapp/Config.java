package com.redfox.webapp;

import com.redfox.webapp.storage.SqlStorage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    private static final File PROPS = new File(getHomeDir(),"config\\resumes.properties");

    private static final Config INSTANCE = new Config();

    private final File storageDir;
    private final SqlStorage storage;

    private Config() {
        try (InputStream is = new FileInputStream(PROPS)) {
            Properties props = new Properties();
            props.load(is);
            storageDir = new File(props.getProperty("storage.dir"));
            storage = new SqlStorage(
                    props.getProperty("db.url"),
                    props.getProperty("db.user"),
                    props.getProperty("db.password")
            );
        } catch (IOException e) {
            throw new IllegalStateException("Invalid com.redfox.webapp.config file " + PROPS.getAbsolutePath());
        }
    }

    public static Config get() {
        return INSTANCE;
    }

    public File getStorageDir() {
        return storageDir;
    }

    public SqlStorage getStorage() {
        return storage;
    }

    private static File getHomeDir() {
        // Get the environment variable which specified in Tomcat VM options.
        String prop = System.getProperty("homeDir");
        File homeDir = new File(prop==null ? "." : prop);
        if (!homeDir.isDirectory()) {
            throw new IllegalStateException(homeDir + " is not directory");
        }
        return homeDir;
    }
}
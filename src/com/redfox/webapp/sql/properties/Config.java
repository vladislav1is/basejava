package com.redfox.webapp.sql.properties;

import com.redfox.webapp.exception.StorageException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

public class Config {

    private String directory;
    private Map<PropertyType, String> properties;

    public Config(String directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        this.directory = directory;
        Path dir = Paths.get(directory);
        if (!Files.exists(dir) || !Files.isWritable(dir)) {
            throw new IllegalArgumentException(dir + "is not exists or is mot writable");
        }
        properties = readProperties();
    }

    public void setDirectory(String directory) {
        this.directory = directory;
        writeProperty(PropertyType.STORAGE_DIR, directory);
    }

    public void writeProperty(PropertyType type, String value) {
        properties.put(type, value);
        try (OutputStream output = new FileOutputStream(directory)) {
            Properties prop = new Properties();
            for (Map.Entry<PropertyType, String> entry : properties.entrySet()) {
                prop.setProperty(entry.getKey().getTitle(), entry.getValue());
            }
            prop.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public String getProperty(PropertyType type) {
        return properties.get(type);
    }

    public Map<PropertyType, String> readProperties() {
        Map<PropertyType, String> map = new EnumMap<>(PropertyType.class);
        try (InputStream input = new FileInputStream(directory)) {
            Properties prop = new Properties();
            prop.load(input);
            for (PropertyType type : PropertyType.values()) {
                map.put(type, prop.getProperty(type.getTitle()));
            }
            return map;
        } catch (IOException ex) {
            throw new StorageException(ex);
        }
    }
}

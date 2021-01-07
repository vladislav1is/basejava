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

public class MyConfig {

    private String directory;
    private Map<MyPropertyType, String> properties;

    public MyConfig(String directory) {
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
        writeProperty(MyPropertyType.STORAGE_DIR, directory);
    }

    public void writeProperty(MyPropertyType type, String value) {
        properties.put(type, value);
        try (OutputStream output = new FileOutputStream(directory)) {
            Properties prop = new Properties();
            for (Map.Entry<MyPropertyType, String> entry : properties.entrySet()) {
                prop.setProperty(entry.getKey().getTitle(), entry.getValue());
            }
            prop.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public String getProperty(MyPropertyType type) {
        return properties.get(type);
    }

    public Map<MyPropertyType, String> readProperties() {
        Map<MyPropertyType, String> map = new EnumMap<>(MyPropertyType.class);
        try (InputStream input = new FileInputStream(directory)) {
            Properties prop = new Properties();
            prop.load(input);
            for (MyPropertyType type : MyPropertyType.values()) {
                map.put(type, prop.getProperty(type.getTitle()));
            }
            return map;
        } catch (IOException ex) {
            throw new StorageException(ex);
        }
    }
}

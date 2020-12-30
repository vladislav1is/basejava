package com.redfox.webapp.sql;

import com.redfox.webapp.sql.properties.Config;
import com.redfox.webapp.sql.properties.PropertyType;
import com.redfox.webapp.storage.AbstractStorageTest;
import com.redfox.webapp.storage.SqlStorage;

public class SqlStorageTest extends AbstractStorageTest {

    private static final Config config = new Config("config/resumes.properties");

    public SqlStorageTest() {
        super(new SqlStorage(config.getProperty(PropertyType.DB_URL),
                config.getProperty(PropertyType.DB_USER),
                config.getProperty(PropertyType.DB_PASSWORD)
        ));
    }
}
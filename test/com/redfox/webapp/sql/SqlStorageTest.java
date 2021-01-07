package com.redfox.webapp.sql;

import com.redfox.webapp.Config;
import com.redfox.webapp.storage.AbstractStorageTest;
import com.redfox.webapp.storage.SqlStorage;

import java.util.Properties;

public class SqlStorageTest extends AbstractStorageTest {

    private static final Properties CONFIG = Config.get().getProps();

    public SqlStorageTest() {
        super(new SqlStorage(CONFIG.getProperty("db.url"),
                CONFIG.getProperty("db.user"),
                CONFIG.getProperty("db.password")));
    }
//    private static final MyConfig CONFIG = new MyConfig("config/resumes.properties");
//
//    public SqlStorageTest() {
//        super(new SqlStorage(CONFIG.getProperty(MyPropertyType.DB_URL),
//                CONFIG.getProperty(MyPropertyType.DB_USER),
//                CONFIG.getProperty(MyPropertyType.DB_PASSWORD)
//        ));
//    }
}
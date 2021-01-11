package com.redfox.webapp.sql;

import com.redfox.webapp.Config;
import com.redfox.webapp.storage.AbstractStorageTest;

public class SqlStorageTest extends AbstractStorageTest {

    public SqlStorageTest() {
        super(Config.get().getStorage());
    }
}
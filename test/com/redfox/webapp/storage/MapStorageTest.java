package com.redfox.webapp.storage;

import java.util.HashMap;

public class MapStorageTest extends AbstractStorageTest {
    public MapStorageTest() {
        super(new MapStorage(new HashMap<>()));
    }
}
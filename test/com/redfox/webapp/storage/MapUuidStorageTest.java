package com.redfox.webapp.storage;

import java.util.HashMap;

public class MapUuidStorageTest extends AbstractMapStorageTest {

    public MapUuidStorageTest() {
        super(new MapUuidStorage(new HashMap<>()));
    }
}
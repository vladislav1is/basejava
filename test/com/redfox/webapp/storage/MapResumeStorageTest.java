package com.redfox.webapp.storage;

import java.util.HashMap;

public class MapResumeStorageTest extends AbstractMapStorageTest {

    public MapResumeStorageTest() {
        super(new MapResumeStorage(new HashMap<>()));
    }
}
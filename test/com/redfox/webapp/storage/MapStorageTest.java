package com.redfox.webapp.storage;

import com.redfox.webapp.model.Resume;

import java.util.Arrays;
import java.util.HashMap;

import static org.junit.Assert.assertArrayEquals;

public class MapStorageTest extends AbstractStorageTest {

    public MapStorageTest() {
        super(new MapStorage(new HashMap<>()));
    }

    @Override
    protected void assertGetAll(Resume[] expected, Resume[] actual) {
        Arrays.sort(actual);
        assertArrayEquals(expected, actual);
    }
}
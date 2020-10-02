package com.redfox.webapp.storage;

import com.redfox.webapp.model.Resume;

import java.util.ArrayList;

import static org.junit.Assert.assertArrayEquals;

public class ListStorageTest extends AbstractStorageTest {

    public ListStorageTest() {
        super(new ListStorage(new ArrayList<>()));
    }

    @Override
    protected void assertGetAll(Resume[] expected, Resume[] actual) {
        assertArrayEquals(expected, actual);
    }
}
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
    public void getAll() {
        Resume[] expectedResumes = {RESUME_1, RESUME_2, RESUME_3};
        Resume[] actualResumes = storage.getAll();
        Arrays.sort(actualResumes, (o1, o2) -> o1.getUuid().compareTo(o2.getUuid()));
        assertArrayEquals(expectedResumes, actualResumes);
    }
}
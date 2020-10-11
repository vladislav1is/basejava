package com.redfox.webapp.storage;

import com.redfox.webapp.model.Resume;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;

public class MapStorageTest extends AbstractStorageTest {

    public MapStorageTest() {
        super(new MapStorage(new HashMap<>()));
    }

    @Override
    public void getAllSorted() {
        Resume[] expectedResumes = {RESUME_1, RESUME_2, RESUME_3};
        List<Resume> actualResumes = storage.getAllSorted();
        Collections.sort(actualResumes, (o1, o2) -> o1.getUuid().compareTo(o2.getUuid()));
        assertArrayEquals(expectedResumes, actualResumes.toArray());
    }
}
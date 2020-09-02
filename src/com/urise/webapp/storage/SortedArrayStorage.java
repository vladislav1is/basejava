package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume r) {

    }

    @Override
    public void delete(String uuid) {

    }

    @Override
    public void update(Resume r) {

    }

    @Override
    public Resume[] getAll() {
        return new Resume[0];
    }

    @Override
    public void clear() {

    }

    @Override
    protected int indexOf(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}

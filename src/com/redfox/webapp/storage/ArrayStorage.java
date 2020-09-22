package com.redfox.webapp.storage;

import com.redfox.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    protected int indexOf(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void insertElement(Resume resume) {
        storage[size] = resume;
    }

    @Override
    protected void fillDeletedElement(String uuid) {
        int index = indexOf(uuid);
        storage[index] = storage[size - 1];
    }
}

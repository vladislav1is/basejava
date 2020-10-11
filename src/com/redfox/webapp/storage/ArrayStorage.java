package com.redfox.webapp.storage;

import com.redfox.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    protected Integer getSearchKey(String fullName, String uuid) {
        throw new UnsupportedOperationException();
    }

    protected void insertElement(int index, Resume resume) {
        storage[size] = resume;
    }

    protected void fillDeletedElement(int index) {
        storage[index] = storage[size];
    }
}

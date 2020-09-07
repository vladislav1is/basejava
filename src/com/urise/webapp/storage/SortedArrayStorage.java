package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int indexOf(String uuid) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void insertElement(Resume resume, int index) {
        index = Math.abs(index) - 1;
        if (storage[index] == null) {
            storage[index] = resume;
        } else {
            System.arraycopy(storage, index, storage, index + 1, size + 1);
            if (storage[index].compareTo(resume) >= 0) {
                storage[index] = resume;
            } else {
                storage[index + 1] = resume;
            }
        }
    }
}

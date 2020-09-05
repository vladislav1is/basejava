package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume resume) {
        Objects.requireNonNull(resume, "resume must not be null");
        int index = indexOf(resume.getUuid());
        String uuid = resume.getUuid();
        if (index >= 0) {
            System.out.printf("Resume %s is already exist\n", uuid);
        } else {
            if (size < storage.length) {
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
                size++;
            } else {
                System.out.printf("Storage overflow, no place for %s\n", uuid);
            }
        }
    }

    @Override
    protected int indexOf(String uuid) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}

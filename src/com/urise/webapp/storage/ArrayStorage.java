package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    public void save(Resume resume) {
        Objects.requireNonNull(resume, "resume must not be null");
        int index = indexOf(resume.getUuid());
        String uuid = resume.getUuid();
        if (index >= 0) {
            System.out.printf("Resume %s is already exist\n", uuid);
        } else {
            if (size < storage.length) {
                storage[size] = resume;
                size++;
            } else {
                System.out.printf("Storage overflow, no place for %s\n", uuid);
            }
        }
    }

    protected int indexOf(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}

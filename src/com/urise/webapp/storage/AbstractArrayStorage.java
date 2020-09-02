package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    private static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    public int size() {
        return size;
    }

    public Resume get(String uuid) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        int index = indexOf(uuid);
        if (index >= 0) {
            return storage[index];
        }
        System.out.printf("Resume %s not exist\n", uuid);
        return null;
    }

    protected abstract int indexOf(String uuid);
}

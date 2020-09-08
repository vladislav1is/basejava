package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
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

    public void save(Resume resume) {
        Objects.requireNonNull(resume, "resume must not be null");
        int index = indexOf(resume.getUuid());
        String uuid = resume.getUuid();
        if (index >= 0) {
            System.out.printf("Resume %s is already exist\n", uuid);
        } else {
            if (size < storage.length) {
                insertElement(resume, index);
                size++;
            } else {
                System.out.printf("Storage overflow, no place for %s\n", uuid);
            }
        }
    }

    public void delete(String uuid) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        int index = indexOf(uuid);
        if (index >= 0) {
            fillDeletedElement(index);
            storage[size - 1] = null;
            size--;
        } else {
            System.out.printf("Resume %s not exist\n", uuid);
        }
    }

    public void update(Resume resume) {
        Objects.requireNonNull(resume, "resume must not be null");
        String uuid = resume.getUuid();
        int index = indexOf(uuid);
        if (index >= 0) {
            storage[index] = resume;
        }
        System.out.printf("Resume %s not exist\n", uuid);
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    protected abstract int indexOf(String uuid);

    protected abstract void insertElement(Resume resume, int index);

    protected abstract void fillDeletedElement(int index);
}

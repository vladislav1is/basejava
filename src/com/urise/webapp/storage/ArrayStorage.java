package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage implements Storage {
    private static final int STORAGE_LIMIT = 10_000;

    private Resume[] storage = new Resume[STORAGE_LIMIT];
    private int size;

    public void save(Resume resume) {
        Objects.requireNonNull(resume, "resume must not be null");
        int index = indexOf(resume.getUuid());
        String uuid = resume.getUuid();
        if (index >= 0) {
            System.out.printf("Resume is already included %s\n", uuid);
        } else {
            if (size < storage.length) {
                storage[size] = resume;
                size++;
            } else {
                System.out.printf("Storage is already fill, no place for %s\n", uuid);
            }
        }
    }

    public Resume get(String uuid) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        int index = indexOf(uuid);
        if (index >= 0) {
            return storage[index];
        }
        System.out.printf("Resume with uuid:%s not found\n", uuid);
        return null;
    }

    public void delete(String uuid) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        int index = indexOf(uuid);
        if (index >= 0) {
            for (int i = index; i < size - 1; i++) {
                storage[i] = storage[i + 1];
            }
            size--;
        } else {
            System.out.printf("Resume with uuid:%s not found\n", uuid);
        }
    }

    public void update(Resume resume) {
        Objects.requireNonNull(resume, "resume must not be null");
        String uuid = resume.getUuid();
        int index = indexOf(uuid);
        if (index >= 0) {
            storage[index] = resume;
        }
        System.out.printf("Resume with uuid:%s not found\n", uuid);
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

    public int size() {
        return size;
    }

    private int indexOf(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}

package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size;

    public void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    public void save(Resume r) {
        Objects.requireNonNull(r, "resume must not be null");
        int index = uuidSearch(r.getUuid());
        if (index >= 0) {
            update(r);
        } else {
            if (size < storage.length) {
                storage[size] = r;
                size++;
            } else {
                System.out.println("Storage is Full");
            }
        }
    }

    public Resume get(String uuid) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        int index = uuidSearch(uuid);
        if (index >= 0) {
            return storage[index];
        }
        System.out.println("Resume not found");
        return null;
    }

    public void delete(String uuid) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        int index = uuidSearch(uuid);
        if (index >= 0) {
            for (int i = index; i < size - 1; i++) {
                storage[i] = storage[i + 1];
            }
            size--;
        } else {
            System.out.println("Nothing to delete");
        }
    }

    public boolean update(Resume resume) {
        Objects.requireNonNull(resume, "resume must not be null");
        int index = uuidSearch(resume.getUuid());
        if (index >= 0) {
            storage[index] = resume;
            return true;
        }
        System.out.println("Nothing to update");
        return false;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] resumes = new Resume[size];
        for (int i = 0; i < size; i++) {
            resumes[i] = storage[i];
        }
        return resumes;
    }

    public int size() {
        return size;
    }

    private int uuidSearch(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}

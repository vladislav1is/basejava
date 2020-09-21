package com.redfox.webapp.storage;

import com.redfox.webapp.exception.StorageException;
import com.redfox.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    static final int STORAGE_LIMIT = 10_000;

    Resume[] storage = new Resume[STORAGE_LIMIT];

    @Override
    protected void addElement(Resume resume) {
        String uuid = resume.getUuid();
        int index = indexOf(uuid);
        if (size < storage.length) {
            insertElement(resume, index);
            size++;
        } else {
            throw new StorageException("Storage overflow", uuid);
        }
    }

    @Override
    protected Resume getElementBy(String uuid) {
        int index = indexOf(uuid);
        return storage[index];
    }

    @Override
    protected void deleteElementBy(String uuid) {
        int index = indexOf(uuid);
        fillDeletedElement(index);
        size--;
        storage[size] = null;
    }

    @Override
    protected void replaceElementBy(String uuid, Resume resume) {
        int index = indexOf(uuid);
        storage[index] = resume;
    }

    @Override
    protected Resume[] getAllElements() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    protected void clearElements() {
        Arrays.fill(storage, 0, size, null);
    }

    protected abstract int indexOf(String uuid);

    protected abstract void insertElement(Resume resume, int index);

    protected abstract void fillDeletedElement(int index);
}
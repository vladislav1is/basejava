package com.redfox.webapp.storage;

import com.redfox.webapp.exception.ExistStorageException;
import com.redfox.webapp.exception.StorageException;
import com.redfox.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    static final int STORAGE_LIMIT = 10_000;

    Resume[] storage = new Resume[STORAGE_LIMIT];

    public void save(Resume resume) {
        String uuid = resume.getUuid();
        int index = indexOf(uuid);
        if (index >= 0) {
            throw new ExistStorageException(uuid);
        } else if (size < storage.length) {
            insertElement(resume, index);
            size++;
        } else {
            throw new StorageException("Storage overflow", uuid);
        }
    }

    @Override
    protected Resume getElementBy(int index) {
        return storage[index];
    }

    @Override
    protected void deleteElementBy(int index) {
        fillDeletedElement(index);
        size--;
        storage[size] = null;
    }

    @Override
    protected void setElementBy(int index, Resume resume) {
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
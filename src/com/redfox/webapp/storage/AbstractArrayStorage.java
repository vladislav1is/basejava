package com.redfox.webapp.storage;

import com.redfox.webapp.exception.StorageException;
import com.redfox.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    static final int STORAGE_LIMIT = 10_000;
    int size;

    Resume[] storage = new Resume[STORAGE_LIMIT];

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public int size() {
        return size;
    }

    @Override
    protected boolean isExist(Object key) {
        return (Integer) key >= 0;
    }

    @Override
    protected void addElement(Object key, Resume resume) {
        if (size < STORAGE_LIMIT) {
            insertElement((Integer) key, resume);
            size++;
        } else {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
    }

    @Override
    protected Resume getElementBy(Object key) {
        return storage[(Integer) key];
    }

    @Override
    protected void deleteElementBy(Object key) {
        fillDeletedElement((Integer) key);
        storage[--size] = null;
    }

    @Override
    protected void updateElementBy(Object key, Resume resume) {
        storage[(Integer) key] = resume;
    }

    protected abstract Integer keyOf(String uuid);

    protected abstract void insertElement(int index, Resume resume);

    protected abstract void fillDeletedElement(int index);
}
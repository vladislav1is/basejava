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
    protected boolean containsElementBy(String uuid) {
        return indexOf(uuid) >= 0;
    }

    @Override
    protected void addElement(Resume resume) {
        String uuid = resume.getUuid();
        if (size < storage.length) {
            insertElement(resume);
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
        fillDeletedElement(uuid);
        size--;
        storage[size] = null;
    }

    @Override
    protected void replaceElementBy(String uuid, Resume resume) {
        int index = indexOf(uuid);
        storage[index] = resume;
    }

    protected abstract int indexOf(String uuid);

    protected abstract void insertElement(Resume resume);

    protected abstract void fillDeletedElement(String uuid);
}
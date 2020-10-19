package com.redfox.webapp.storage;

import com.redfox.webapp.exception.StorageException;
import com.redfox.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    static final int STORAGE_LIMIT = 10_000;
    int size;

    Resume[] storage = new Resume[STORAGE_LIMIT];

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return (Integer) searchKey >= 0;
    }

    @Override
    protected void doSave(Object searchKey, Resume resume) {
        if (size < STORAGE_LIMIT) {
            insertElement((Integer) searchKey, resume);
            size++;
        } else {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage[(Integer) searchKey];
    }

    @Override
    protected void doDelete(Object searchKey) {
        fillDeletedElement((Integer) searchKey);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected void doUpdate(Object searchKey, Resume resume) {
        storage[(Integer) searchKey] = resume;
    }

    @Override
    protected List<Resume> doCopyAll() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }

    protected abstract void insertElement(int index, Resume resume);

    protected abstract void fillDeletedElement(int index);
}
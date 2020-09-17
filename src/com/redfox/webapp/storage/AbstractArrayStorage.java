package com.redfox.webapp.storage;

import com.redfox.webapp.exception.ExistStorageException;
import com.redfox.webapp.exception.NotExistStorageException;
import com.redfox.webapp.exception.StorageException;
import com.redfox.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    static final int STORAGE_LIMIT = 10_000;

    Resume[] storage = new Resume[STORAGE_LIMIT];
    int size;

    public int size() {
        return size;
    }

    public Resume get(String uuid) {
        int index = indexOf(uuid);
        if (index >= 0) {
            return storage[index];
        }
        throw new NotExistStorageException(uuid);
    }

    public void save(Resume resume) {
        int index = indexOf(resume.getUuid());
        String uuid = resume.getUuid();
        if (index >= 0) {
            throw new ExistStorageException(uuid);
        } else if (size < storage.length) {
            insertElement(resume, index);
            size++;
        } else {
            throw new StorageException("Storage overflow", uuid);
        }
    }

    public void delete(String uuid) {
        int index = indexOf(uuid);
        if (index >= 0) {
            fillDeletedElement(index);
            size--;
            storage[size] = null;
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    public void update(Resume resume) {
        String uuid = resume.getUuid();
        int index = indexOf(uuid);
        if (index >= 0) {
            storage[index] = resume;
        } else {
            throw new NotExistStorageException(uuid);
        }
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

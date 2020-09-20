package com.redfox.webapp.storage;

import com.redfox.webapp.exception.NotExistStorageException;
import com.redfox.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {
    int size;

    @Override
    public abstract void save(Resume resume);

    @Override
    public Resume get(String uuid) {
        int index = indexOf(uuid);
        if (index >= 0) {
            return getElementBy(index);
        }
        throw new NotExistStorageException(uuid);
    }

    @Override
    public void delete(String uuid) {
        int index = indexOf(uuid);
        if (index >= 0) {
            deleteElementBy(index);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public void update(Resume resume) {
        String uuid = resume.getUuid();
        int index = indexOf(uuid);
        if (index >= 0) {
            setElementBy(index, resume);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public Resume[] getAll() {
        return getAllElements();
    }

    @Override
    public void clear() {
        clearElements();
        size = 0;
    }

    public int size() {
        return size;
    }

    protected abstract int indexOf(String uuid);

    protected abstract Resume getElementBy(int index);

    protected abstract void deleteElementBy(int index);

    protected abstract void setElementBy(int index, Resume resume);

    protected abstract Resume[] getAllElements();

    protected abstract void clearElements();
}

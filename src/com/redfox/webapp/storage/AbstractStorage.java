package com.redfox.webapp.storage;

import com.redfox.webapp.exception.ExistStorageException;
import com.redfox.webapp.exception.NotExistStorageException;
import com.redfox.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {
    int size;

    @Override
    public void save(Resume resume) {
        String uuid = resume.getUuid();
        if (containsElementBy(uuid)) {
            throw new ExistStorageException(uuid);
        } else {
            addElement(resume);
        }
    }

    @Override
    public Resume get(String uuid) {
        if (!containsElementBy(uuid)) {
            throw new NotExistStorageException(uuid);
        }
        return getElementBy(uuid);
    }

    @Override
    public void delete(String uuid) {
        if (!containsElementBy(uuid)) {
            throw new NotExistStorageException(uuid);
        }
        deleteElementBy(uuid);
    }

    @Override
    public void update(Resume resume) {
        String uuid = resume.getUuid();
        if (!containsElementBy(uuid)) {
            throw new NotExistStorageException(uuid);
        }
        replaceElementBy(uuid, resume);
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

    private boolean containsElementBy(String uuid) {
        int index = indexOf(uuid);
        return (index >= 0) ? true : false;
    }

    protected abstract void addElement(Resume resume);

    protected abstract int indexOf(String uuid);

    protected abstract Resume getElementBy(String uuid);

    protected abstract void deleteElementBy(String uuid);

    protected abstract void replaceElementBy(String uuid, Resume resume);

    protected abstract Resume[] getAllElements();

    protected abstract void clearElements();
}

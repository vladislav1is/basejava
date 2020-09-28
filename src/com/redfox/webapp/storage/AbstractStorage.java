package com.redfox.webapp.storage;

import com.redfox.webapp.exception.ExistStorageException;
import com.redfox.webapp.exception.NotExistStorageException;
import com.redfox.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void save(Resume resume) {
        String uuid = resume.getUuid();
        Object key = keyOf(uuid);
        if (isExist(key)) {
            throw new ExistStorageException(uuid);
        }
        addElement(key, resume);
    }

    @Override
    public Resume get(String uuid) {
        Object key = checkForExist(uuid);
        return getElementBy(key);
    }

    @Override
    public void delete(String uuid) {
        Object key = checkForExist(uuid);
        deleteElementBy(key);
    }

    @Override
    public void update(Resume resume) {
        Object key = checkForExist(resume.getUuid());
        updateElementBy(key, resume);
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public abstract Resume[] getAll();

    @Override
    public abstract void clear();

    @Override
    public abstract int size();

    protected abstract Object keyOf(String uuid);

    protected abstract boolean isExist(Object key);

    protected abstract void addElement(Object key, Resume resume);

    protected abstract Resume getElementBy(Object key);

    protected abstract void deleteElementBy(Object key);

    protected abstract void updateElementBy(Object key, Resume resume);

    private Object checkForExist(String uuid) {
        Object key = keyOf(uuid);
        if (!isExist(key)) {
            throw new NotExistStorageException(uuid);
        }
        return key;
    }
}

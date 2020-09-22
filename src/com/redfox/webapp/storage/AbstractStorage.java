package com.redfox.webapp.storage;

import com.redfox.webapp.exception.ExistStorageException;
import com.redfox.webapp.exception.NotExistStorageException;
import com.redfox.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void save(Resume resume) {
        String uuid = resume.getUuid();
        if (containsElementBy(uuid)) {
            throw new ExistStorageException(uuid);
        }
        addElement(resume);
    }

    @Override
    public Resume get(String uuid) {
        notExistedUuid(uuid);
        return getElementBy(uuid);
    }

    @Override
    public void delete(String uuid) {
        notExistedUuid(uuid);
        deleteElementBy(uuid);
    }

    @Override
    public void update(Resume resume) {
        String uuid = resume.getUuid();
        notExistedUuid(uuid);
        replaceElementBy(uuid, resume);
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

    protected abstract boolean containsElementBy(String uuid);

    protected abstract void addElement(Resume resume);

    protected abstract Resume getElementBy(String uuid);

    protected abstract void deleteElementBy(String uuid);

    protected abstract void replaceElementBy(String uuid, Resume resume);

    private void notExistedUuid(String uuid) {
        if (!containsElementBy(uuid)) {
            throw new NotExistStorageException(uuid);
        }
    }
}

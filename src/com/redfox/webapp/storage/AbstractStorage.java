package com.redfox.webapp.storage;

import com.redfox.webapp.exception.ExistStorageException;
import com.redfox.webapp.exception.NotExistStorageException;
import com.redfox.webapp.model.Resume;

import java.util.List;

public abstract class AbstractStorage implements Storage {

    @Override
    public void save(Resume resume) {
        Object searchKey = getNotExistedKey(resume.getUuid(), resume.getFullName());
        doSave(searchKey, resume);
    }

    @Override
    public Resume get(String uuid) {
        Object searchKey = getExistedKey(uuid);
        return doGet(searchKey);
    }

    @Override
    public void delete(String uuid) {
        Object searchKey = getExistedKey(uuid);
        doDelete(searchKey);
    }

    @Override
    public void update(Resume resume) {
        Object searchKey = getExistedKey(resume.getUuid(), resume.getFullName());
        doUpdate(searchKey, resume);
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public abstract List<Resume> getAllSorted();

    @Override
    public abstract void clear();

    @Override
    public abstract int size();

    protected abstract Object getSearchKey(String uuid);

    protected abstract Object getSearchKey(String uuid, String fullName);

    protected abstract boolean isExist(Object searchKey);

    protected abstract void doSave(Object searchKey, Resume resume);

    protected abstract Resume doGet(Object searchKey);

    protected abstract void doDelete(Object searchKey);

    protected abstract void doUpdate(Object searchKey, Resume resume);

    private Object getExistedKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

//    private Object getNotExistedKey(String uuid) {
//        Object searchKey = getSearchKey(uuid);
//        if (isExist(searchKey)) {
//            throw new ExistStorageException(uuid);
//        }
//        return searchKey;
//    }

    private Object getExistedKey(String uuid, String fullName) {
        Object searchKey = getSearchKey(uuid, fullName);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private Object getNotExistedKey(String uuid, String fullName) {
        Object searchKey = getSearchKey(uuid, fullName);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }
}

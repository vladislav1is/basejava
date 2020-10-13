package com.redfox.webapp.storage;

import com.redfox.webapp.exception.ExistStorageException;
import com.redfox.webapp.exception.NotExistStorageException;
import com.redfox.webapp.model.Resume;

import java.util.*;

public abstract class AbstractStorage implements Storage {


    @Override
    public void save(Resume resume) {
        Object searchKey = getNotExistedKey(resume.getUuid());
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
        Object searchKey = getExistedKey(resume.getUuid());
        doUpdate(searchKey, resume);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumeList = Arrays.asList(getAll());
        Collections.sort(resumeList, (o1, o2) -> {
            int result = o1.getFullName().compareTo(o2.getFullName());
            if (result == 0) result = o1.getUuid().compareTo(o2.getUuid());
            return result;
        });
        return resumeList;
    }

    @Override
    public abstract void clear();

    @Override
    public abstract int size();

    protected abstract Object getSearchKey(String uuid);

    protected abstract boolean isExist(Object searchKey);

    private Object getExistedKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private Object getNotExistedKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    protected abstract void doSave(Object searchKey, Resume resume);

    protected abstract Resume doGet(Object searchKey);

    protected abstract void doDelete(Object searchKey);

    protected abstract void doUpdate(Object searchKey, Resume resume);

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    protected abstract Resume[] getAll();
}

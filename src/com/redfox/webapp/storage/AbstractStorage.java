package com.redfox.webapp.storage;

import com.redfox.webapp.exception.ExistStorageException;
import com.redfox.webapp.exception.NotExistStorageException;
import com.redfox.webapp.model.Resume;

import java.util.*;

public abstract class AbstractStorage<SK> implements Storage {

    @Override
    public void save(Resume resume) {
        SK searchKey = getNotExistedKey(resume.getUuid());
        doSave(searchKey, resume);
    }

    @Override
    public Resume get(String uuid) {
        SK searchKey = getExistedKey(uuid);
        return doGet(searchKey);
    }

    @Override
    public void delete(String uuid) {
        SK searchKey = getExistedKey(uuid);
        doDelete(searchKey);
    }

    @Override
    public void update(Resume resume) {
        SK searchKey = getExistedKey(resume.getUuid());
        doUpdate(searchKey, resume);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumeList = doCopyAll();
        Collections.sort(resumeList, Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid));
        return resumeList;
    }

    private SK getExistedKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private SK getNotExistedKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    protected abstract SK getSearchKey(String uuid);

    protected abstract boolean isExist(SK searchKey);

    protected abstract void doSave(SK searchKey, Resume resume);

    protected abstract Resume doGet(SK searchKey);

    protected abstract void doDelete(SK searchKey);

    protected abstract void doUpdate(SK searchKey, Resume resume);

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    protected abstract List<Resume> doCopyAll();
}

package com.redfox.webapp.storage;

import com.redfox.webapp.model.Resume;

import java.util.Map;

public class MapResumeStorage extends AbstractMapStorage<Resume> {

    public MapResumeStorage(Map<String, Resume> storage) {
        super(storage);
    }

    @Override
    protected Resume getSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected boolean isExist(Resume searchKey) {
        return searchKey != null;
    }

    @Override
    protected void doSave(Resume searchKey, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume doGet(Resume searchKey) {
        return searchKey;
    }

    @Override
    protected void doDelete(Resume searchKey) {
        storage.remove(searchKey.getUuid());
    }

    @Override
    protected void doUpdate(Resume searchKey, Resume resume) {
        storage.replace(resume.getUuid(), resume);
    }
}

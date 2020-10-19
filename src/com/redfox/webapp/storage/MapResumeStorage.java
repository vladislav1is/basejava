package com.redfox.webapp.storage;

import com.redfox.webapp.model.Resume;

import java.util.Map;

public class MapResumeStorage extends AbstractMapStorage {

    public MapResumeStorage(Map<String, Resume> storage) {
        super(storage);
    }

    @Override
    protected Resume getSearchKey(String uuid) {
        return storage.get(uuid);
    }


    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }

    @Override
    protected void doSave(Object searchKey, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return (Resume) searchKey;
    }

    @Override
    protected void doDelete(Object searchKey) {
        storage.remove(((Resume) searchKey).getUuid());
    }

    @Override
    protected void doUpdate(Object searchKey, Resume resume) {
        storage.replace(resume.getUuid(), resume);
    }
}

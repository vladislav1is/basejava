package com.redfox.webapp.storage;

import com.redfox.webapp.model.Resume;

import java.util.Map;

public class MapUuidStorage extends AbstractMapStorage {

    public MapUuidStorage(Map<String, Resume> storage) {
        super(storage);
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }


    @Override
    protected boolean isExist(Object searchKey) {
        return storage.containsKey((String) searchKey);
    }

    @Override
    protected void doSave(Object searchKey, Resume resume) {
        storage.put((String) searchKey, resume);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage.get((String) searchKey);
    }

    @Override
    protected void doDelete(Object searchKey) {
        storage.remove((String) searchKey);
    }

    @Override
    protected void doUpdate(Object searchKey, Resume resume) {
        storage.replace((String) searchKey, resume);
    }
}

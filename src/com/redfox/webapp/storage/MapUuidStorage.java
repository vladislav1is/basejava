package com.redfox.webapp.storage;

import com.redfox.webapp.model.Resume;

import java.util.Map;

public class MapUuidStorage extends AbstractMapStorage<String> {

    public MapUuidStorage(Map<String, Resume> storage) {
        super(storage);
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(String searchKey) {
        return storage.containsKey(searchKey);
    }

    @Override
    protected void doSave(String searchKey, Resume resume) {
        storage.put(searchKey, resume);
    }

    @Override
    protected Resume doGet(String searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected void doDelete(String searchKey) {
        storage.remove(searchKey);
    }

    @Override
    protected void doUpdate(String searchKey, Resume resume) {
        storage.replace(searchKey, resume);
    }
}

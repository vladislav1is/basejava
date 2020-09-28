package com.redfox.webapp.storage;

import com.redfox.webapp.model.Resume;

import java.util.Map;

public class MapStorage extends AbstractStorage {
    private final Map<String, Resume> storage;

    public MapStorage(Map<String, Resume> storage) {
        this.storage = storage;
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[storage.size()]);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected String keyOf(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(Object key) {
        return storage.containsKey(key);
    }

    @Override
    protected void addElement(Object key, Resume resume) {
        storage.put((String) key, resume);
    }

    @Override
    protected Resume getElementBy(Object key) {
        return storage.get(key);
    }

    @Override
    protected void deleteElementBy(Object key) {
        storage.remove(key);
    }

    @Override
    protected void updateElementBy(Object key, Resume resume) {
        storage.replace((String) key, resume);
    }
}

package com.redfox.webapp.storage;

import com.redfox.webapp.model.Resume;

import java.util.Map;

public class MapStorage extends AbstractStorage {
    private Map<String, Resume> storage;

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
    protected boolean containsElementBy(String uuid) {
        return storage.containsKey(uuid);
    }

    @Override
    protected void addElement(Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getElementBy(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void deleteElementBy(String uuid) {
        storage.remove(uuid);
    }

    @Override
    protected void replaceElementBy(String uuid, Resume resume) {
        storage.replace(uuid, resume);
    }
}

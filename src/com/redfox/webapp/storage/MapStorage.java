package com.redfox.webapp.storage;

import com.redfox.webapp.model.Resume;

import java.util.Map;

public class MapStorage extends AbstractStorage {
    Map<String, Resume> storage;

    public MapStorage(Map<String, Resume> storage) {
        this.storage = storage;
    }

    @Override
    protected void addElement(Resume resume) {
        String uuid = resume.getUuid();
        storage.put(uuid, resume);
        size++;
    }

    @Override
    protected Resume getElementBy(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void deleteElementBy(String uuid) {
        size--;
        storage.remove(uuid);
    }

    @Override
    protected void replaceElementBy(String uuid, Resume resume) {
        storage.replace(uuid, resume);
    }

    @Override
    protected Resume[] getAllElements() {
        return storage.values().toArray(new Resume[size]);
    }

    @Override
    protected void clearElements() {
        storage.clear();
    }

    @Override
    protected int indexOf(String uuid) {
        if (!storage.containsKey(uuid)) {
            return -1;
        }
        return 0;
    }
}

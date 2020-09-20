package com.redfox.webapp.storage;

import com.redfox.webapp.exception.ExistStorageException;
import com.redfox.webapp.exception.NotExistStorageException;
import com.redfox.webapp.model.Resume;

import java.util.Map;

public class MapStorage extends AbstractStorage {
    Map<String, Resume> storage;

    public MapStorage(Map<String, Resume> storage) {
        this.storage = storage;
    }

    @Override
    public void save(Resume resume) {
        String key = resume.getUuid();
        if (!storage.containsKey(key)) {
            storage.put(key, resume);
            size++;
        } else {
            throw new ExistStorageException(key);
        }
    }

    @Override
    public Resume get(String uuid) {
        if (storage.containsKey(uuid)) {
            return storage.get(uuid);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public void delete(String uuid) {
        if (storage.containsKey(uuid)) {
            size--;
            storage.remove(uuid);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public void update(Resume resume) {
        String key = resume.getUuid();
        if (storage.containsKey(key)) {
            storage.replace(key, resume);
        } else {
            throw new NotExistStorageException(key);
        }
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[size]);
    }

    @Override
    public void clear() {
        storage.clear();
        size = 0;
    }

    @Override
    protected Resume getElementBy(int index) {
        return null;
    }

    @Override
    protected void setElementBy(int index, Resume resume) {

    }

    @Override
    protected void deleteElementBy(int index) {

    }

    @Override
    protected void clearElements() {

    }

    @Override
    protected Resume[] getAllElements() {
        return new Resume[0];
    }

    @Override
    protected int indexOf(String uuid) {
        return 0;
    }
}

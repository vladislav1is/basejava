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
        return (Resume[]) storage.values().toArray();
    }

    @Override
    public void clear() {
        storage.clear();
        size = 0;
    }

    @Override
    public int size() {
        return storage.size();
    }
}

package com.redfox.webapp.storage;

import com.redfox.webapp.exception.ExistStorageException;
import com.redfox.webapp.exception.NotExistStorageException;
import com.redfox.webapp.model.Resume;

import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> storage;

    public ListStorage(List<Resume> list) {
        this.storage = list;
    }

    @Override
    public void save(Resume resume) {
        if (!storage.contains(resume)) {
            storage.add(resume);
            size++;
        } else {
            throw new ExistStorageException(resume.getUuid());
        }
    }

    @Override
    public Resume get(String uuid) {
        int index = indexOf(uuid);
        if (index >= 0) {
            return storage.get(index);
        }
        throw new NotExistStorageException(uuid);
    }

    @Override
    public void delete(String uuid) {
        int index = indexOf(uuid);
        if (index >= 0) {
            size--;
            storage.remove(index);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public void update(Resume resume) {
        int index = storage.indexOf(resume);
        if (index >= 0) {
            storage.set(index, resume);
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[size]);
    }

    @Override
    public void clear() {
        storage.clear();
        size = 0;
    }

    private int indexOf(String uuid) {
        int size = storage.size();
        for (int i = 0; i < size; i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
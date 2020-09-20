package com.redfox.webapp.storage;

import com.redfox.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {
    int size;

    @Override
    public abstract void save(Resume resume);

    @Override
    public abstract Resume get(String uuid);

    @Override
    public abstract void delete(String uuid);

    @Override
    public abstract void update(Resume resume);

    @Override
    public abstract Resume[] getAll();

    @Override
    public abstract void clear();

    public int size() {
        return size;
    }
}

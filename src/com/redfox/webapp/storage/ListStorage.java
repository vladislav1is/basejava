package com.redfox.webapp.storage;

import com.redfox.webapp.model.Resume;

import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> storage;

    public ListStorage(List<Resume> list) {
        this.storage = list;
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[storage.size()]);
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
    protected Integer keyOf(String uuid) {
        int size = storage.size();
        for (int i = 0; i < size; i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean isExist(Object key) {
        return (Integer) key >= 0;
    }

    @Override
    protected void addElement(Object key, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected Resume getElementBy(Object key) {
        return storage.get((Integer) key);
    }

    @Override
    protected void deleteElementBy(Object key) {
        int index = (Integer) key;
        storage.remove(index);
    }

    @Override
    protected void updateElementBy(Object key, Resume resume) {
        storage.set((Integer) key, resume);
    }
}
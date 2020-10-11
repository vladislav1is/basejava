package com.redfox.webapp.storage;

import com.redfox.webapp.model.Resume;

import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> storage;

    public ListStorage(List<Resume> list) {
        this.storage = list;
    }

    @Override
    public List<Resume> getAllSorted() {
        return storage.subList(0, size());
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
    protected Integer getSearchKey(String uuid) {
        int size = storage.size();
        for (int i = 0; i < size; i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected Object getSearchKey(String fullName, String uuid) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected boolean isExist(Object key) {
        return (Integer) key >= 0;
    }

    @Override
    protected void doSave(Object key, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected Resume doGet(Object key) {
        return storage.get((Integer) key);
    }

    @Override
    protected void doDelete(Object key) {
        int index = (Integer) key;
        storage.remove(index);
    }

    @Override
    protected void doUpdate(Object key, Resume resume) {
        storage.set((Integer) key, resume);
    }
}
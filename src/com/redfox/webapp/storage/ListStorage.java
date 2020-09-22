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
    protected boolean containsElementBy(String uuid) {
        return indexOf(uuid) >= 0;
    }

    @Override
    protected void addElement(Resume resume) {
        storage.add(resume);
    }

    @Override
    protected Resume getElementBy(String uuid) {
        int index = indexOf(uuid);
        return storage.get(index);
    }

    @Override
    protected void deleteElementBy(String uuid) {
        int index = indexOf(uuid);
        storage.remove(index);
    }

    @Override
    protected void replaceElementBy(String uuid, Resume resume) {
        int index = indexOf(uuid);
        storage.set(index, resume);
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
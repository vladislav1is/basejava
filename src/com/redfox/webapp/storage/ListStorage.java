package com.redfox.webapp.storage;

import com.redfox.webapp.model.Resume;

import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> storage;

    public ListStorage(List<Resume> list) {
        this.storage = list;
    }

    @Override
    protected void addElement(Resume resume) {
        storage.add(resume);
        size++;
    }

    @Override
    protected Resume getElementBy(String uuid) {
        int index = indexOf(uuid);
        return storage.get(index);
    }

    @Override
    protected void deleteElementBy(String uuid) {
        int index = indexOf(uuid);
        size--;
        storage.remove(index);
    }

    @Override
    protected void replaceElementBy(String uuid, Resume resume) {
        int index = indexOf(uuid);
        storage.set(index, resume);
    }

    @Override
    protected Resume[] getAllElements() {
        return storage.toArray(new Resume[size]);
    }

    @Override
    protected void clearElements() {
        storage.clear();
    }

    protected int indexOf(String uuid) {
        int size = storage.size();
        for (int i = 0; i < size; i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
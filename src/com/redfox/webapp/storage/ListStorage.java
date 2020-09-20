package com.redfox.webapp.storage;

import com.redfox.webapp.exception.ExistStorageException;
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
    protected Resume getElementBy(int index) {
        return storage.get(index);
    }

    @Override
    protected void setElementBy(int index, Resume resume) {
        storage.set(index, resume);
    }

    @Override
    protected void deleteElementBy(int index) {
        size--;
        storage.remove(index);
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

    @Override
    protected void clearElements() {
        storage.clear();
    }

    @Override
    protected Resume[] getAllElements() {
        return storage.toArray(new Resume[size]);
    }
}
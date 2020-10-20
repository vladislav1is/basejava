package com.redfox.webapp.storage;

import com.redfox.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class AbstractMapStorage<SK> extends AbstractStorage<SK> {

    protected final Map<String, Resume> storage;

    protected AbstractMapStorage(Map<String, Resume> storage) {
        this.storage = storage;
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
    public List<Resume> doCopyAll() {
        return new ArrayList<>(storage.values());
    }
}

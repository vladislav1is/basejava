package com.redfox.webapp.storage;

import com.redfox.webapp.model.Resume;

import java.util.List;

/**
 * Array based storage for Resumes
 */
public interface Storage {

    void save(Resume resume);

    Resume get(String uuid);

    void delete(String uuid);

    void update(Resume resume);

    /**
     * @return List sorted by name, contains only Resumes in storage (without null)
     */
    List<Resume> getAllSorted();

    void clear();

    int size();
}

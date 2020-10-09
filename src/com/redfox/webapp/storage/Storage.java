package com.redfox.webapp.storage;

import com.redfox.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public interface Storage {

    void save(Resume resume);

    Resume get(String uuid);

    void delete(String uuid);

    void update(Resume resume);

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll();
    //return List sorted by name
    //List<Resume> getAllSorted();

    void clear();

    int size();
}

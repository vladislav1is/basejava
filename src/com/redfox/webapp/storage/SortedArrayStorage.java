package com.redfox.webapp.storage;

import com.redfox.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int indexOf(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void insertElement(Resume resume, int index) {
//      http://codereview.stackexchange.com/questions/36221/binary-search-for-inserting-in-array#answer-36239
        index = Math.abs(index) - 1;
        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = resume;
    }

    @Override
    protected void fillDeletedElement(int index) {
        int numMoved = size - index - 1;
        if (numMoved >= 0) {
            System.arraycopy(storage, index + 1, storage, index, numMoved);
        }
    }
}

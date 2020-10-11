package com.redfox.webapp.storage;

import com.redfox.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Array based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {

    private static final Comparator<Resume> RESUME_COMPARATOR = (o1, o2) -> {
        int result = o1.getFullName().compareTo(o2.getFullName());
        if (result == 0) result = o1.getUuid().compareTo(o2.getUuid());
        return result;
    };

    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    protected Integer getSearchKey(String uuid, String fullName) {
        Resume searchKey = new Resume(uuid, fullName);
        return Arrays.binarySearch(storage, 0, size, searchKey, RESUME_COMPARATOR);
    }

    protected void insertElement(int index, Resume resume) {
//      http://codereview.stackexchange.com/questions/36221/binary-search-for-inserting-in-array#answer-36239
        index = Math.abs(index) - 1;
        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = resume;
    }

    protected void fillDeletedElement(int index) {
        int numMoved = size - index - 1;
        if (numMoved >= 0) {
            System.arraycopy(storage, index + 1, storage, index, numMoved);
        }
    }
}

package com.redfox.webapp.storage;

import com.redfox.webapp.exception.StorageException;
import com.redfox.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Test;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveOverFlow() {
//      https://stackoverflow.com/questions/3869954/whats-the-actual-use-of-fail-in-junit-test-case
        try {
            for (int i = storage.size(); i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume("Name" + i));
            }
        } catch (StorageException e) {
            Assert.fail("Exception not thrown");
        }
        storage.save(new Resume("uuid10_001"));
    }
}
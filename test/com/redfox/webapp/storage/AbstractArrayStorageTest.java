package com.redfox.webapp.storage;

import com.redfox.webapp.exception.ExistStorageException;
import com.redfox.webapp.exception.NotExistStorageException;
import com.redfox.webapp.exception.StorageException;
import com.redfox.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractArrayStorageTest {
    private Storage storage;
    private Resume[] resumes;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final Resume RESUME_1 = new Resume(UUID_1);
    private static final Resume RESUME_2 = new Resume(UUID_2);
    private static final Resume RESUME_3 = new Resume(UUID_3);
    private static final Resume RESUME_4 = new Resume(UUID_4);

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void get() {
        Assert.assertEquals(RESUME_1, storage.get(UUID_1));
    }

    @Test
    public void save() {
        resumes = new Resume[]{RESUME_1, RESUME_2, RESUME_3, RESUME_4};
        storage.save(RESUME_4);
        Assert.assertEquals(4, storage.size());
        Assert.assertArrayEquals(resumes, storage.getAll());
    }

    @Test
    public void delete() {
        resumes = new Resume[]{RESUME_1, RESUME_2};
        storage.delete(UUID_3);
        Assert.assertEquals(2, storage.size());
        Assert.assertArrayEquals(resumes, storage.getAll());
    }

    @Test
    public void update() {
        storage.update(RESUME_3);
        Assert.assertEquals(RESUME_3, storage.get(UUID_3));
    }

    @Test
    public void getAll() {
        resumes = new Resume[]{RESUME_1, RESUME_2, RESUME_3};
        Assert.assertArrayEquals(resumes, storage.getAll());
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertArrayEquals(new Resume[]{}, storage.getAll());
        Assert.assertEquals(0, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_2);
    }

    @Test(expected = StorageException.class)
    public void saveStorage() {
//      https://stackoverflow.com/questions/3869954/whats-the-actual-use-of-fail-in-junit-test-case
        try {
            for (int i = storage.size(); i < 10_000; i++) {
                storage.save(new Resume("uuid" + (i + 1)));
            }
        } catch (StorageException e) {
            Assert.fail("Exception not thrown");
        }
        storage.save(new Resume("uuid10_001"));
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(UUID_4);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(RESUME_4);
    }
}
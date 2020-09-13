package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractArrayStorageTest {
    private Storage storage;

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    @Before
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void get() {
        Assert.assertEquals(new Resume(UUID_1), storage.get(UUID_1));
    }

    @Test
    public void save() {
        Resume resume = new Resume("uuid4");
        Resume[] resumes = new Resume[]{new Resume(UUID_1), new Resume(UUID_2), new Resume((UUID_3)), resume};
        storage.save(resume);
        Assert.assertEquals(4, storage.size());
        Assert.assertArrayEquals(resumes, storage.getAll());
    }

    @Test
    public void delete() {
        Resume[] resumes = new Resume[]{new Resume(UUID_1), new Resume(UUID_2)};
        storage.delete(UUID_3);
        Assert.assertEquals(2, storage.size());
        Assert.assertArrayEquals(resumes, storage.getAll());
    }

    @Test//(expected = NotExistStorageException.class)
    public void update() throws Exception {
        Resume resume = new Resume(UUID_3);
        storage.update(resume);
        Assert.assertEquals(resume, storage.get(UUID_3));
    }

    @Test
    public void getAll() {
        Resume[] resumes = new Resume[]{new Resume(UUID_1), new Resume(UUID_2), new Resume((UUID_3))};
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

    @Test
    public void saveStorageOverFlow() {
//      https://stackoverflow.com/questions/3869954/whats-the-actual-use-of-fail-in-junit-test-case
        try {
            for (int i = storage.size(); i < 10_000; i++) {
                Resume r = new Resume("uuid" + (i + 1));
                storage.save(r);
                System.out.println(r.getUuid());
            }
            storage.save(new Resume("uuid10_001"));
            Assert.fail("Exception not thrown");
        } catch (StorageException e) {
            Assert.assertTrue(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail();
        }
    }
}
package com.redfox.webapp;

import com.redfox.webapp.model.Resume;
import com.redfox.webapp.storage.MapUuidStorage;
import com.redfox.webapp.storage.Storage;

import java.util.HashMap;

/**
 * Test for your com.urise.webapp.storage.ArrayStorage implementation
 */
public class MainTestArrayStorage {
    static final Storage ARRAY_STORAGE = new MapUuidStorage(new HashMap<>());

    public static void main(String[] args) {
        Resume r1 = new Resume("uuid1", "Nick");
        Resume r2 = new Resume("uuid2", "John");
        Resume r3 = new Resume("uuid3", "Ed");
        Resume r4 = new Resume("uuid4", "John");
        Resume r5 = new Resume("uuid5", "Bruce");
        Resume r6 = new Resume("uuid6", "Fred");

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r4);
        ARRAY_STORAGE.save(r5);
        ARRAY_STORAGE.save(r3);
        ARRAY_STORAGE.save(r6);
        ARRAY_STORAGE.save(r2);

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

//        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        printAll();
        ARRAY_STORAGE.delete(r1.getUuid());
        printAll();
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAllSorted()) {
            System.out.println(r.getFullName());
        }
    }
}

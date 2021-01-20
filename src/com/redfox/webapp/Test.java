package com.redfox.webapp;

import com.redfox.webapp.model.Resume;
import com.redfox.webapp.storage.SqlStorage;

import java.util.UUID;

import static com.redfox.webapp.MainResumeTestData.constructResume;

public class Test {

    private static final String UUID_1 = UUID.randomUUID().toString();
    private static final String UUID_2 = UUID.randomUUID().toString();
    private static final String UUID_3 = UUID.randomUUID().toString();
    private static final String UUID_4 = UUID.randomUUID().toString();

    protected static final Resume R1;
    protected static final Resume R2;
    protected static final Resume R3;
    protected static final Resume R4;

    static {
        R1 = constructResume(UUID_1, "Name1");
        R2 = constructResume(UUID_2, "Name2");
        R3 = constructResume(UUID_3, "Name3");
        R4 = constructResume(UUID_4, "Name4");
    }

    public static void main(String[] args) {
        SqlStorage storage = Config.get().getStorage();
        storage.clear();
        storage.save(R1);
        storage.save(R2);
        storage.save(R3);

        Resume newResume = new Resume(UUID_2, "Peter");
//        newResume.addContact(ContactType.GITHUB, "1");
//        newResume.addContact(ContactType.MAIL, "2");
        storage.update(newResume);
        storage.get(UUID_2);
    }
}

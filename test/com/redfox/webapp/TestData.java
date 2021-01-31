package com.redfox.webapp;

import com.redfox.webapp.model.ContactType;
import com.redfox.webapp.model.Resume;

import java.util.UUID;

import static com.redfox.webapp.MainResumeTestData.constructResume;

public class TestData {
    public static final String UUID_1 = UUID.randomUUID().toString();
    public static final String UUID_2 = UUID.randomUUID().toString();
    public static final String UUID_3 = UUID.randomUUID().toString();
    public static final String UUID_4 = UUID.randomUUID().toString();

    public static final Resume R1;
    public static final Resume R2;
    public static final Resume R3;
    public static final Resume R4;

    static {
        R1 = constructResume(UUID_1, "Name1");
        R2 = constructResume(UUID_2, "Name2");
        R3 = constructResume(UUID_3, "Name3");
        R4 = constructResume(UUID_4, "Name4");

        R4.addContact(ContactType.MAIL, "mail@ya.ru");
        R4.addContact(ContactType.PHONE,"444444");
        R4.addContact(ContactType.SKYPE, "skype");
    }
}

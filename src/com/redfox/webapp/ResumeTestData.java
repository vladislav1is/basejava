package com.redfox.webapp;

import com.redfox.webapp.model.*;

import java.time.Month;
import java.util.Map;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = constructResume("uuid1", "Boris");
        printAll(resume);
    }

    public static Resume constructResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);

        resume.addContact(ContactType.MAIL, new Link("mail@ya.ru"));
        resume.addContact(ContactType.PHONE, new Link("1111"));
        resume.addContact(ContactType.SKYPE, new Link("skype2"));

        resume.addSection(SectionType.OBJECTIVE, new TextSection("Objective1"));
        resume.addSection(SectionType.PERSONAL, new TextSection("Personal data"));
        resume.addSection(SectionType.ACHIEVEMENT, new ListTextSection("Achievement11", "Achievement12", "Achievement13"));
        resume.addSection(SectionType.QUALIFICATIONS, new ListTextSection("Java", "SQL", "JavaScript"));
        resume.addSection(SectionType.EXPERIENCE,
                new OrganizationSection(
                        new Organization("Organization11", "http://Organization11.ru",
                                new Organization.Experience(2005, Month.JANUARY, "position1", "content2"),
                                new Organization.Experience(2001, Month.MARCH, 2005, Month.JANUARY, "position2", "content2")
                        )
                )
        );
        resume.addSection(SectionType.EDUCATION,
                new OrganizationSection(
                        new Organization("Institute",
                                new Organization.Experience(1996, Month.JANUARY, 2000, Month.DECEMBER, "aspirant"),
                                new Organization.Experience(2001, Month.MARCH, 2005, Month.JANUARY, "student", "IT facultet")
                )
        ));
        return resume;
    }

    static void printAll(Resume resume) {
        System.out.println(resume.getFullName());
        System.out.println();
        for (Map.Entry<ContactType, Link> contact : resume.getContacts().entrySet()) {
            System.out.println(contact.getKey().getTitle() + " " + contact.getValue().getName() + " " + contact.getValue().getUrl());
        }
        System.out.println();
        for (Map.Entry<SectionType, AbstractSection> section : resume.getSections().entrySet()) {
            System.out.println(section.getKey().getTitle() + "\n" + section.getValue());
        }
    }
}

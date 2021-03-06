package com.redfox.webapp;

import com.redfox.webapp.model.*;

import java.time.Month;
import java.util.Map;

public class MainResumeTestData {
    public static void main(String[] args) {
        Resume resume = constructResume("uuid1", "Boris");
        printAll(resume);
    }

    public static Resume constructResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);

        resume.setContact(ContactType.PHONE, "8_777_777_77_77");
        resume.setContact(ContactType.SKYPE, "skype1");
        resume.setContact(ContactType.MAIL, "mail@1");
        resume.setContact(ContactType.LINKEDIN, "linkedin1");
        resume.setContact(ContactType.GITHUB, "github1");
        resume.setContact(ContactType.STACKOVERFLOW, "linkedin1");
        resume.setContact(ContactType.HOMEPAGE, "https://www.mypage.xxx");

        resume.setSection(SectionType.OBJECTIVE, new TextSection("Objective1"));
        resume.setSection(SectionType.PERSONAL, new TextSection("Personal data1"));
        resume.setSection(SectionType.ACHIEVEMENT, new ListTextSection("Achievement11", "Achievement12", "Achievement13"));
        resume.setSection(SectionType.QUALIFICATIONS, new ListTextSection("Java", "SQL", "JavaScript"));
        resume.setSection(SectionType.EXPERIENCE,
                new OrganizationSection(
                        new Organization("Organization1", "http://Organization1.ru",
                                new Organization.Experience(2020, Month.FEBRUARY, "position11", "content11")
                        ),
                        new Organization("Organization2", "http://Organization2.ru",
                                new Organization.Experience(2019, Month.JULY, 2020, Month.FEBRUARY, "position21", "content21")
                        ),
                        new Organization("Organization3", "http://Organization3.ru",
                                new Organization.Experience(2016, Month.SEPTEMBER, 2019, Month.JULY, "position31", "content31")
                        )
                )
        );
        resume.setSection(SectionType.EDUCATION,
                new OrganizationSection(
                        new Organization("Сourses",
                                new Organization.Experience(2020, Month.AUGUST, "listener"),
                                new Organization.Experience(2013, Month.AUGUST, 2013, Month.NOVEMBER, "listener"),
                                new Organization.Experience(2011, Month.NOVEMBER, 2012, Month.FEBRUARY, "listener")
                        ),
                        new Organization("Institute",
                                new Organization.Experience(1996, Month.SEPTEMBER, 2000, Month.DECEMBER, "aspirant"),
                                new Organization.Experience(2001, Month.SEPTEMBER, 2005, Month.JULY, "student", "IT facultet"),
                                new Organization.Experience(2001, Month.SEPTEMBER, 2005, Month.JULY, "student", "IT facultet")
                        )
                ));
        return resume;
    }

    static void printAll(Resume resume) {
        System.out.println(resume.getFullName());
        System.out.println();
        for (Map.Entry<ContactType, String> contact : resume.getContacts().entrySet()) {
            System.out.println(contact.getKey().getTitle() + " " + contact.getValue());
        }
        System.out.println();
        for (Map.Entry<SectionType, AbstractSection> section : resume.getSections().entrySet()) {
            System.out.println(section.getKey().getTitle() + "\n" + section.getValue());
        }
    }
}

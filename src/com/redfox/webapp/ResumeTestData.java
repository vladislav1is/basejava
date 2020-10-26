package com.redfox.webapp;

import com.redfox.webapp.model.*;

import java.time.YearMonth;
import java.util.Arrays;
import java.util.Map;

public class ResumeTestData {
    private static final Resume resume = new Resume("Boris");

    public static void main(String[] args) {
        Map<SectionType, Content> resumeSections = resume.getSections();
        Map<ContactType, String> resumeContacts = resume.getContacts();

        resumeContacts.put(ContactType.PHONE, "number");
        resumeContacts.put(ContactType.SKYPE, "skype_profile");
        resumeContacts.put(ContactType.MAIL, "mail_profile");
        resumeContacts.put(ContactType.LINCKEDIN, "linckedin_profile");
        resumeContacts.put(ContactType.GITHUB, "github_profile");
        resumeContacts.put(ContactType.STACKOVERFLOW, "github_profile");
        resumeContacts.put(ContactType.HOMEPAGE, "homepage");

        resumeSections.put(SectionType.OBJECTIVE, new TextContent("text"));
        resumeSections.put(SectionType.PERSONAL, new TextContent("text"));
        resumeSections.put(SectionType.ACHIEVEMENT, new ListTextContent(Arrays.asList("text1", "text2", "tex3")));
        resumeSections.put(SectionType.QUALIFICATIONS, new ListTextContent(Arrays.asList("text1", "text2", "tex3")));

        String dateMonth = "7";
        String dateYear = "1914";
        YearMonth startDate = YearMonth.of(Integer.valueOf(dateYear), Integer.valueOf(dateMonth));
        YearMonth endDate = YearMonth.of(Integer.valueOf(dateYear), Integer.valueOf(dateMonth));
        resumeSections.put(SectionType.EXPERIENCE, new ListCompanyContent(Arrays.asList(
                new Company("name1", startDate, endDate, "header1", "desc1"),
                new Company("name2", startDate, endDate, "header2", "desc2")
        )));
        resumeSections.put(SectionType.EDUCATION, new ListCompanyContent(Arrays.asList(
                new Company("name1", startDate, endDate, "header2")
//                new CompanyTest("name", Arrays.asList(startDate, startDate), Arrays.asList(endDate, endDate), Arrays.asList("header1", "header2"))
        )));
        printAll();
    }

    static void printAll() {
        System.out.println(resume.getFullName());
        System.out.println();
        for (Map.Entry contact : resume.getContacts().entrySet()) {
            System.out.println(contact.getKey() + " " + contact.getValue());
        }
        System.out.println();
        for (Map.Entry section : resume.getSections().entrySet()) {
            System.out.println(section.getKey() + ":" + section.getValue());
        }
    }
}

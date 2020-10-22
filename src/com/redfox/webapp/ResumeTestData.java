package com.redfox.webapp;

import com.redfox.webapp.model.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ResumeTestData {
    public static void main(String[] args) {
        Map<ContactType, String> contacts = new HashMap<>();
        contacts.put(ContactType.PHONE, "telNumber");
        contacts.put(ContactType.SKYPE, "skypeName");
        contacts.put(ContactType.MAIL, "mailName");

        Map<SectionType, Content> sections = new HashMap<>();
        sections.put(SectionType.PERSONAL, new TextContent("text"));

        sections.put(SectionType.OBJECTIVE, new BoldTextContent("boldText"));

        sections.put(SectionType.ACHIEVEMENT, new ListTextContent(Arrays.asList(
                new TextContent("text"),
                new TextContent("text")
        )));

        sections.put(SectionType.QUALIFICATIONS, new ListTextContent(Arrays.asList(
                new TextContent("text"),
                new TextContent("text")
        )));

        HashMap<TextContent, ListTextContent> experienceContent = new HashMap<>();
        experienceContent.put(new HeaderTextContent("headerText1"), new ListTextContent(Arrays.asList(
                new TextDateContent("dateText"),
                new BoldTextContent("boldText"),
                new TextContent("text")
        )));
        experienceContent.put(new HeaderTextContent("headerText2"), new ListTextContent(Arrays.asList(
                new TextDateContent("dateText"),
                new BoldTextContent("boldText"),
                new TextContent("text")
        )));
        sections.put(SectionType.EXPERIENCE, new MapTextContent(experienceContent));

        HashMap<TextContent, ListTextContent> educationContent = new HashMap<>();
        educationContent.put(new HeaderTextContent("headerText1"), new ListTextContent(Arrays.asList(
                new TextDateContent("dateText"), new BoldTextContent("boldText"),
                new TextDateContent("dateText"), new BoldTextContent("boldText")
        )));
        sections.put(SectionType.EDUCATION, new MapTextContent(educationContent));

        Resume resume = new Resume("Boris", contacts, sections);

        System.out.println(resume.getFullName() + "\n");
        for (Map.Entry<ContactType, String> contact : resume.getContacts().entrySet()) {
            System.out.println(contact.getKey().getTitle() + " : " + contact.getValue());
        }
        System.out.println();
        for (Map.Entry<SectionType, Content> section : resume.getSections().entrySet()) {
            System.out.println(section.getKey().getTitle());
            System.out.println(section.getValue().getText());
        }
    }
}

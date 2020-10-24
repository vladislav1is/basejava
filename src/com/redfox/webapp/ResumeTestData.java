package com.redfox.webapp;

import com.redfox.webapp.model.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResumeTestData {
    public static void main(String[] args) {
        // вход. данные: название, логин
        Map<String, String> contacts = new HashMap<>();
        contacts.put("phone", "telNumber");
        contacts.put("skype", "skypeName");
        contacts.put("mail", "mailName");

        // входные данные
        Map<SectionType, List<Content>> sections = new HashMap<>();
        sections.put(SectionType.PERSONAL, Arrays.asList(new TextContent("text")));

        sections.put(SectionType.OBJECTIVE, Arrays.asList(new TextContent("text")));

        sections.put(SectionType.ACHIEVEMENT, Arrays.asList(
                new TextContent("text"),
                new TextContent("text")
        ));

        sections.put(SectionType.QUALIFICATIONS, Arrays.asList(
                new TextContent("text"),
                new TextContent("text")
        ));

        sections.put(SectionType.EXPERIENCE, Arrays.asList(
                new HeaderTextContent("header1", Arrays.asList(new DateTextContent(
                        new BoldTextContent("text", "boldText"), "1914 7"))),
                new HeaderTextContent("header2", Arrays.asList(new DateTextContent(
                        new BoldTextContent("text", "boldText"), "1914 7")))
        ));

        sections.put(SectionType.EDUCATION, Arrays.asList(
                new HeaderTextContent("header1", Arrays.asList(
                        new DateTextContent(new BoldTextContent("boldText"), "1914 7"),
                        new DateTextContent(new BoldTextContent("boldText"), "1914 7")
                ))
        ));

        Resume resume = new Resume("Boris", contacts, sections);

        System.out.println(resume.getFullName() + "\n");
        for (Map.Entry<String, String> contact : resume.getContacts().entrySet()) {
            System.out.println(contact.getKey() + " : " + contact.getValue());
        }
        System.out.println();
        for (Map.Entry<SectionType, List<Content>> section : resume.getSections().entrySet()) {
//            TODO:....
        }
    }
}

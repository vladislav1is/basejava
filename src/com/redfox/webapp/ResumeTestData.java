package com.redfox.webapp;

import com.redfox.webapp.model.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ResumeTestData {
    public static void main(String[] args) {
        List<String> contacts = Arrays.asList("foxfish@mail.ru", "+7(926) 495-0482");
        List<SectionType> sections = getSections();
        Resume resume = new Resume("Boris", contacts, sections);

        resume.getFullName();
        for (String contact : resume.getContacts()) {
            System.out.println(contact);
        }
        for (SectionType sectionType : resume.getSections()) {
            SectionType tmp = sectionType;
            System.out.println(tmp.getTitle() + "\n" + tmp.getSection().getText());
        }
    }

    public static List<SectionType> getSections() {
        SectionType personal = SectionType.PERSONAL;
        personal.setContent(new TextContent("text"));

        SectionType objective = SectionType.OBJECTIVE;
        objective.setContent(new TextBoldContent("boldText"));

        SectionType achievement = SectionType.ACHIEVEMENT;
        achievement.setContent(new ListTextContent(Arrays.asList(
                new TextContent("text"),
                new TextContent("text")
        )));
        SectionType qualifications = SectionType.QUALIFICATIONS;
        qualifications.setContent(new ListTextContent(Arrays.asList(
                new TextContent("text"),
                new TextContent("text")
        )));
        SectionType experience = SectionType.EXPERIENCE;
        HashMap<TextContent, ListTextContent> experienceContent = new HashMap<>();
        experienceContent.put(new TextHeaderContent("headerText1"), new ListTextContent(Arrays.asList(
                new TextDateContent("dateText"),
                new TextBoldContent("boldText"),
                new TextContent("text")
        )));
        experienceContent.put(new TextHeaderContent("headerText2"), new ListTextContent(Arrays.asList(
                new TextDateContent("dateText"),
                new TextBoldContent("boldText"),
                new TextContent("text")
        )));
        experience.setContent(new MapTextContent(experienceContent));

        SectionType education = SectionType.EDUCATION;
        HashMap<TextContent, ListTextContent> educationContent = new HashMap<>();
        educationContent.put(new TextHeaderContent("headerText1"), new ListTextContent(Arrays.asList(
                new TextDateContent("dateText"), new TextBoldContent("boldText"),
                new TextDateContent("dateText"), new TextBoldContent("boldText")
        )));
        education.setContent(new MapTextContent(educationContent));

        return Arrays.asList(personal, objective, achievement, qualifications, experience, education);
    }
}

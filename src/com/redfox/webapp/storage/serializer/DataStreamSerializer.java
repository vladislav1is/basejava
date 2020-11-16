package com.redfox.webapp.storage.serializer;

import com.redfox.webapp.model.*;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements SerializerStrategy {
    @Override
    public void doWrite(OutputStream os, Resume resume) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, Link> contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, Link> contact : contacts.entrySet()) {
                dos.writeUTF(contact.getKey().name());
                dos.writeUTF(contact.getValue().getName());
            }
            // TODO: implements sections
            Map<SectionType, AbstractSection> sections = resume.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, AbstractSection> section : sections.entrySet()) {
                dos.writeUTF(section.getKey().name());

                AbstractSection abstractSection = section.getValue();
                dos.writeUTF(abstractSection.getClass().getName());
                if (abstractSection instanceof TextSection) {
                    dos.writeUTF(((TextSection) abstractSection).getContent());
                }
                if (abstractSection instanceof ListTextSection) {
                    List<String> items = ((ListTextSection) abstractSection).getItems();
                    dos.writeInt(items.size());
                    for (String item : items) {
                        dos.writeUTF(item);
                    }
                }
                if (abstractSection instanceof OrganizationSection) {
                    List<Organization> organizations = ((OrganizationSection) abstractSection).getOrganizations();
                    dos.writeInt(organizations.size());
                    for (Organization organization : organizations) {
                        Link link = organization.getHomePage();
                        dos.writeUTF(link.getName());
                        //
                        if (link.getUrl() == null) {
                            dos.writeUTF("null");
                        } else {
                            dos.writeUTF(link.getUrl());
                        }
                        //
                        List<Organization.Experience> positions = organization.getPositions();
                        dos.writeInt(positions.size());
                        for (Organization.Experience position : positions) {
                            dos.writeUTF(position.getTitle());
                            //
                            if (position.getDescription() == null) {
                                dos.writeUTF("null");
                            } else {
                                dos.writeUTF(position.getDescription());
                            }
                            //
                            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
                            dos.writeUTF(position.getStartDate().format(formatter));
                            dos.writeUTF(position.getEndDate().format(formatter));
                        }
                    }
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int contactsSize = dis.readInt();
            for (int i = 0; i < contactsSize; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), new Link(dis.readUTF()));
            }
            // TODO: implements sections
            int sectionSize = dis.readInt();
            for (int i = 0; i < sectionSize; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                Class<? extends AbstractSection> clazz = null;
                try {
                    clazz = (Class<? extends AbstractSection>) Class.forName(dis.readUTF());
                    AbstractSection section = clazz.getConstructor().newInstance();

                    if (section instanceof TextSection) {
                        ((TextSection) section).setContent(dis.readUTF());
                    }
                    if (section instanceof ListTextSection) {
                        int textsNum = dis.readInt();
                        ((ListTextSection) section).setItems(new ArrayList<>());
                        for (int j = 0; j < textsNum; j++) {
                            ((ListTextSection) section).addItem(dis.readUTF());
                        }
                    }
                    if (section instanceof OrganizationSection) {
                        int orgsNum = dis.readInt();
                        ((OrganizationSection) section).setOrganizations(new ArrayList<>());
                        for (int j = 0; j < orgsNum; j++) {
                            String linkName = dis.readUTF();
                            String linkUrl = dis.readUTF();
                            if (linkUrl.equals("null")) {
                                linkUrl = null;
                            }
                            //
                            int posNum = dis.readInt();
                            List<Organization.Experience> experiences = new ArrayList<>();
                            for (int k = 0; k < posNum; k++) {
                                Organization.Experience experience = new Organization.Experience();
                                experience.setTitle(dis.readUTF());
                                //
                                String description = dis.readUTF();
                                if (description.equals("null")) {
                                    description = null;
                                }
                                experience.setDescription(description);
                                //
                                DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
                                experience.setStartDate(LocalDate.parse(dis.readUTF(), formatter));
                                experience.setEndDate(LocalDate.parse(dis.readUTF(), formatter));
                                experiences.add(experience);
                            }
                            ((OrganizationSection) section).addItem(new Organization(linkName, linkUrl, experiences));
                        }
                    }
                    resume.addSection(sectionType, section);
                } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            return resume;
        }
    }
}

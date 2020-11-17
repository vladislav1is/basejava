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
            writeContacts(dos, resume);
            writeSections(dos, resume);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            readContacts(dis, resume);
            readSections(dis, resume);
            return resume;
        }
    }

    private void writeContacts(DataOutputStream dos, Resume resume) throws IOException {
        Map<ContactType, Link> contacts = resume.getContacts();
        dos.writeInt(contacts.size());
        for (Map.Entry<ContactType, Link> contact : contacts.entrySet()) {
            dos.writeUTF(contact.getKey().name());
            dos.writeUTF(contact.getValue().getName());
        }
//        contacts.entrySet().stream()
//                .forEach(contact -> {
//                    try {
//                        dos.writeUTF(contact.getKey().name());
//                        dos.writeUTF(contact.getValue().getName());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                });
    }

    private void writeSections(DataOutputStream dos, Resume resume) throws IOException {
        Map<SectionType, AbstractSection> sections = resume.getSections();
        dos.writeInt(sections.size());
        for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
            dos.writeUTF(entry.getKey().name());
            AbstractSection section = entry.getValue();
            writeSectionClassName(dos, section);
            if (section instanceof TextSection) {
                writeTextSection(dos, (TextSection) section);
            }
            if (section instanceof ListTextSection) {
                writeListTextSection(dos, (ListTextSection) section);
            }
            if (section instanceof OrganizationSection) {
                writeOrganizationSection(dos, (OrganizationSection) section);
            }
        }
    }

    private void writeSectionClassName(DataOutputStream dos, AbstractSection section) throws IOException {
        dos.writeUTF(section.getClass().getName());
    }

    private void writeTextSection(DataOutputStream dos, TextSection section) throws IOException {
        dos.writeUTF(section.getContent());
    }

    private void writeListTextSection(DataOutputStream dos, ListTextSection section) throws IOException {
        List<String> items = section.getItems();
        dos.writeInt(items.size());
        for (String item : items) {
            dos.writeUTF(item);
        }
    }

    private void writeOrganizationSection(DataOutputStream dos, OrganizationSection section) throws IOException {
        List<Organization> organizations = section.getOrganizations();
        dos.writeInt(organizations.size());
        for (Organization organization : organizations) {
            Link link = organization.getHomePage();
            dos.writeUTF(link.getName());
            dos.writeUTF(String.valueOf(link.getUrl()));
            List<Organization.Experience> positions = organization.getPositions();
            dos.writeInt(positions.size());
            for (Organization.Experience position : positions) {
                dos.writeUTF(position.getTitle());
                dos.writeUTF(String.valueOf(position.getDescription()));
                DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
                dos.writeUTF(position.getStartDate().format(formatter));
                dos.writeUTF(position.getEndDate().format(formatter));
            }
        }
    }

    private void readContacts(DataInputStream dis, Resume resume) throws IOException {
        int contactsSize = dis.readInt();
        for (int i = 0; i < contactsSize; i++) {
            resume.addContact(ContactType.valueOf(dis.readUTF()), new Link(dis.readUTF()));
        }
    }

    private void readSections(DataInputStream dis, Resume resume) throws IOException {
        int sectionSize = dis.readInt();
        for (int i = 0; i < sectionSize; i++) {
            SectionType sectionType = SectionType.valueOf(dis.readUTF());
            AbstractSection section = readSectionInstance(dis);
            if (section instanceof TextSection) {
                readTextSection(dis, (TextSection) section);
            }
            if (section instanceof ListTextSection) {
                readListTextSection(dis, (ListTextSection) section);
            }
            if (section instanceof OrganizationSection) {
                readOrganizationSection(dis, (OrganizationSection) section);
            }
            resume.addSection(sectionType, section);
        }
    }

    private AbstractSection readSectionInstance(DataInputStream dis) throws IOException {
        Class<?> clazz;
        AbstractSection section = null;
        try {
            clazz = readSectionClassName(dis);
            section = (AbstractSection) clazz.getConstructor().newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return section;
    }

    private Class<?> readSectionClassName(DataInputStream dis) throws IOException, ClassNotFoundException {
        return Class.forName(dis.readUTF());
    }

    private void readTextSection(DataInputStream dis, TextSection section) throws IOException {
        section.setContent(dis.readUTF());
    }

    private void readListTextSection(DataInputStream dis, ListTextSection section) throws IOException {
        int textsNum = dis.readInt();
        section.setItems(new ArrayList<>());
        for (int j = 0; j < textsNum; j++) {
            section.addItem(dis.readUTF());
        }
    }

    private void readOrganizationSection(DataInputStream dis, OrganizationSection section) throws IOException {
        int orgsNum = dis.readInt();
        section.setOrganizations(new ArrayList<>());
        for (int j = 0; j < orgsNum; j++) {
            String linkName = dis.readUTF();
            String linkUrl = dis.readUTF();
            linkUrl = (linkUrl.equals("null")) ? null : linkUrl;
            int posNum = dis.readInt();
            List<Organization.Experience> experiences = new ArrayList<>();
            for (int k = 0; k < posNum; k++) {
                Organization.Experience experience = new Organization.Experience();
                experience.setTitle(dis.readUTF());
                String description = dis.readUTF();
                experience.setDescription((description.equals("null")) ? null : description);
                DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
                experience.setStartDate(LocalDate.parse(dis.readUTF(), formatter));
                experience.setEndDate(LocalDate.parse(dis.readUTF(), formatter));
                experiences.add(experience);
            }
            section.addItem(new Organization(linkName, linkUrl, experiences));
        }
    }
}

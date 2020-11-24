package com.redfox.webapp.storage.serializer;

import com.redfox.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.redfox.webapp.model.SectionType.valueOf;

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


        //writeWithExeption(dos, FuncMeth<T> );

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
            SectionType sectionType = entry.getKey();
            dos.writeUTF(sectionType.name());
            AbstractSection section = entry.getValue();
            switch (sectionType) {
                case OBJECTIVE:
                case PERSONAL:
                    writeTextSection(dos, (TextSection) section);
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    writeListTextSection(dos, (ListTextSection) section);
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    writeOrganizationSection(dos, (OrganizationSection) section);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + sectionType);
            }
        }
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
            String linkUrl = link.getUrl();
            dos.writeUTF((linkUrl == null) ? "null" : linkUrl);
            List<Organization.Experience> positions = organization.getPositions();
            dos.writeInt(positions.size());
            for (Organization.Experience position : positions) {
                String description = position.getDescription();
                dos.writeUTF((description == null) ? "null" : description);
                DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
                dos.writeUTF(position.getStartDate().format(formatter));
                dos.writeUTF(position.getEndDate().format(formatter));
                dos.writeUTF(position.getTitle());
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
            SectionType sectionType = valueOf(dis.readUTF());
            AbstractSection section;
            switch (sectionType) {
                case OBJECTIVE:
                case PERSONAL:
                    section = new TextSection();
                    readTextSection(dis, (TextSection) section);
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    section = new ListTextSection();
                    readListTextSection(dis, (ListTextSection) section);
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    section = new OrganizationSection();
                    readOrganizationSection(dis, (OrganizationSection) section);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + sectionType);
            }
            resume.addSection(sectionType, section);
        }
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
                String description = dis.readUTF();
                DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
                Organization.Experience experience = new Organization.Experience(
                        LocalDate.parse(dis.readUTF(), formatter),
                        LocalDate.parse(dis.readUTF(), formatter),
                        dis.readUTF(),
                        (description.equals("null")) ? null : description
                );
                experiences.add(experience);
            }
            section.addItem(new Organization(linkName, linkUrl, experiences));
        }
    }
}

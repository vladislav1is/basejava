package com.redfox.webapp.storage.serializer;

import com.redfox.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
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
        Map<ContactType, String> contacts = resume.getContacts();
        writeWithException(contacts.entrySet(), dos, contact -> {
            dos.writeUTF(contact.getKey().name());
            dos.writeUTF(contact.getValue());
        });
    }

    private void writeSections(DataOutputStream dos, Resume resume) throws IOException {
        Map<SectionType, AbstractSection> sections = resume.getSections();
        writeWithException(sections.entrySet(), dos, section -> {
            SectionType sectionType = section.getKey();
            dos.writeUTF(sectionType.name());
            AbstractSection sectionValue = section.getValue();
            switch (sectionType) {
                case OBJECTIVE:
                case PERSONAL:
                    writeTextSection(dos, (TextSection) sectionValue);
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    writeListTextSection(dos, (ListTextSection) sectionValue);
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    writeOrganizationSection(dos, (OrganizationSection) sectionValue);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + sectionType);
            }
        });
    }

    private void writeTextSection(DataOutputStream dos, TextSection sectionValue) throws IOException {
        dos.writeUTF(sectionValue.getContent());
    }

    private void writeListTextSection(DataOutputStream dos, ListTextSection sectionValue) throws IOException {
        List<String> items = sectionValue.getItems();
        writeWithException(items, dos, dos::writeUTF);
    }

    private void writeOrganizationSection(DataOutputStream dos, OrganizationSection sectionValue) throws IOException {
        List<Organization> organizations = sectionValue.getOrganizations();
        writeWithException(organizations, dos, organization -> {
            Link link = organization.getHomePage();
            dos.writeUTF(link.getName());
            String linkUrl = link.getUrl();
            dos.writeUTF((linkUrl == null) ? "null" : linkUrl);
            List<Organization.Experience> positions = organization.getPositions();
            writeWithException(positions, dos, position -> {
                String description = position.getDescription();
                dos.writeUTF((description == null) ? "null" : description);
                DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
                dos.writeUTF(position.getStartDate().format(formatter));
                dos.writeUTF(position.getEndDate().format(formatter));
                dos.writeUTF(position.getTitle());
            });
        });

    }

    private void readContacts(DataInputStream dis, Resume resume) throws IOException {
        readWithException(resume, dis, r -> r.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
    }

    private void readSections(DataInputStream dis, Resume resume) throws IOException {
        readWithException(resume, dis, r -> {
            SectionType sectionType = valueOf(dis.readUTF());
            AbstractSection sectionValue;
            switch (sectionType) {
                case OBJECTIVE:
                case PERSONAL:
                    sectionValue = new TextSection();
                    readTextSection(dis, (TextSection) sectionValue);
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    sectionValue = new ListTextSection();
                    readListTextSection(dis, (ListTextSection) sectionValue);
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    sectionValue = new OrganizationSection();
                    readOrganizationSection(dis, (OrganizationSection) sectionValue);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + sectionType);
            }
            resume.addSection(sectionType, sectionValue);
        });
    }

    private void readTextSection(DataInputStream dis, TextSection sectionValue) throws IOException {
        sectionValue.setContent(dis.readUTF());
    }

    private void readListTextSection(DataInputStream dis, ListTextSection sectionValue) throws IOException {
        readWithException(sectionValue, dis, sections -> sections.addItem(dis.readUTF()));
    }

    private void readOrganizationSection(DataInputStream dis, OrganizationSection sectionValue) throws IOException {
        readWithException(sectionValue, dis, section -> {
            String linkName = dis.readUTF();
            String linkUrl = dis.readUTF();
            linkUrl = (linkUrl.equals("null")) ? null : linkUrl;
            List<Organization.Experience> experiences = new ArrayList<>();

            readWithException(experiences, dis, item -> {
                String description = dis.readUTF();
                DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
                Organization.Experience experience = new Organization.Experience(
                        LocalDate.parse(dis.readUTF(), formatter),
                        LocalDate.parse(dis.readUTF(), formatter),
                        dis.readUTF(),
                        (description.equals("null")) ? null : description
                );
                experiences.add(experience);
            });
            sectionValue.addItem(new Organization(linkName, linkUrl, experiences));
        });
    }

    private <C, T extends IOException> void writeWithException(Collection<C> source, DataOutputStream dos, FunctionWithIOExceptions<C, T> function) throws IOException {
        dos.writeInt(source.size());
        for (C item : source) {
            function.apply(item);
        }
    }

    private <C, T extends IOException> void readWithException(C source, DataInputStream dis, FunctionWithIOExceptions<C, T> function) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            function.apply(source);
        }
    }
}

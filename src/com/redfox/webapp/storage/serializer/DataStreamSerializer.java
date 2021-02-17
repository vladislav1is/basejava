package com.redfox.webapp.storage.serializer;

import com.redfox.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
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

    private void writeContacts(DataOutputStream dos, Resume resume) throws IOException {
        Map<ContactType, String> contacts = resume.getContacts();
        writeCollectionWithException(contacts.entrySet(), dos, contact -> {
            dos.writeUTF(contact.getKey().name());
            dos.writeUTF(contact.getValue());
        });
    }

    private void writeSections(DataOutputStream dos, Resume resume) throws IOException {
        Map<SectionType, AbstractSection> sections = resume.getSections();
        writeCollectionWithException(sections.entrySet(), dos, section -> {
            SectionType sectionType = section.getKey();
            AbstractSection sectionValue = section.getValue();
            dos.writeUTF(sectionType.name());
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
        writeCollectionWithException(items, dos, dos::writeUTF);
    }

    private void writeOrganizationSection(DataOutputStream dos, OrganizationSection sectionValue) throws IOException {
        List<Organization> organizations = sectionValue.getOrganizations();
        writeCollectionWithException(organizations, dos, organization -> {
            Link link = organization.getHomePage();
            dos.writeUTF(link.getName());
            dos.writeUTF(link.getUrl());

            List<Organization.Experience> positions = organization.getPositions();
            writeCollectionWithException(positions, dos, position -> {
                writeLocalDate(dos, position.getStartDate());
                writeLocalDate(dos, position.getEndDate());
                dos.writeUTF(position.getTitle());
                dos.writeUTF(position.getDescription());
            });
        });
    }

    private void writeLocalDate(DataOutputStream dos, LocalDate date) throws IOException {
        dos.writeInt(date.getYear());
        dos.writeInt(date.getMonth().getValue());
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

    private void readContacts(DataInputStream dis, Resume resume) throws IOException {
        readCollectionWithException(resume, dis, r -> r.setContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
    }

    private void readSections(DataInputStream dis, Resume resume) throws IOException {
        readCollectionWithException(resume, dis, r -> {
            SectionType sectionType = valueOf(dis.readUTF());
            AbstractSection sectionValue;
            switch (sectionType) {
                case OBJECTIVE:
                case PERSONAL:
                    sectionValue = readTextSection(dis);
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    sectionValue = readListTextSection(dis);
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    sectionValue = readOrganizationSection(dis);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + sectionType);
            }
            resume.setSection(sectionType, sectionValue);
        });
    }

    private TextSection readTextSection(DataInputStream dis) throws IOException {
        return new TextSection(dis.readUTF());
    }

    private ListTextSection readListTextSection(DataInputStream dis) throws IOException {
        return new ListTextSection(readListWithException(dis, () -> dis.readUTF()));
    }

    private OrganizationSection readOrganizationSection(DataInputStream dis) throws IOException {
        return new OrganizationSection(readListWithException(dis, () -> new Organization(
                new Link(dis.readUTF(), dis.readUTF()),
                DataStreamSerializer.this.readListWithException(dis, () -> new Organization.Experience(
                        DataStreamSerializer.this.readLocalDate(dis), DataStreamSerializer.this.readLocalDate(dis), dis.readUTF(), dis.readUTF()
                ))
        )));
    }

    private LocalDate readLocalDate(DataInputStream dis) throws IOException {
        return LocalDate.of(dis.readInt(), dis.readInt(), 1);
    }

    @FunctionalInterface
    private interface ConsumerWithIOExceptions<C, T extends IOException> {
        void accept(C item) throws T;
    }

    private <C, T extends IOException> void writeCollectionWithException(Collection<C> source, DataOutputStream dos, ConsumerWithIOExceptions<C, T> function) throws IOException {
        dos.writeInt(source.size());
        for (C item : source) {
            function.accept(item);
        }
    }

    private <C, T extends IOException> void readCollectionWithException(C source, DataInputStream dis, ConsumerWithIOExceptions<C, T> function) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            function.accept(source);
        }
    }

    @FunctionalInterface
    private interface SupplierWithIOExceptions<T> {
        T get() throws IOException;
    }

    private <T> List<T> readListWithException(DataInputStream dis, SupplierWithIOExceptions<T> reader) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(reader.get());
        }
        return list;
    }
}

package com.redfox.webapp.storage.serializer;

import com.redfox.webapp.exception.StorageException;
import com.redfox.webapp.model.Resume;

import java.io.*;

public class ObjectStreamSerializer implements SerializerStrategy {
    @Override
    public void doWrite(OutputStream os, Resume resume) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(resume);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(is)) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Error read resume", null, e);
        }
    }
}

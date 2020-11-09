package com.redfox.webapp.storage;

import com.redfox.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface SerializableStrategy {
    void doWrite(OutputStream os, Resume resume) throws IOException;
    Resume doRead(InputStream is) throws IOException;
}


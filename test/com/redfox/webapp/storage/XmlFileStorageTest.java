package com.redfox.webapp.storage;

import com.redfox.webapp.storage.serializer.XmlStreamSerializer;

public class XmlFileStorageTest extends AbstractStorageTest {

    public XmlFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new XmlStreamSerializer()));
    }
}
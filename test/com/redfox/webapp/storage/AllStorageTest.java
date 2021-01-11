package com.redfox.webapp.storage;

import com.redfox.webapp.sql.SqlStorageTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(
        {
                ArrayStorageTest.class,
                SortedArrayStorageTest.class,
                ListStorageTest.class,
                MapUuidStorageTest.class,
                MapResumeStorageTest.class,
                ObjectFileStorageTest.class,
                ObjectPathStorageTest.class,
                DataPathStorageTest.class,
                XmlPathStorageTest.class,
                JsonPathStorageTest.class,
                SqlStorageTest.class
        }
)
public class AllStorageTest {
}
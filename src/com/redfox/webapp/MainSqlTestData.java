package com.redfox.webapp;

import com.redfox.webapp.model.Resume;
import com.redfox.webapp.storage.SqlStorage;
import com.redfox.webapp.sql.properties.DBConfiguration;
import com.redfox.webapp.sql.properties.PropertyType;

import java.util.Arrays;

public class MainSqlTestData {

    public static void main(String[] args) {
        DBConfiguration config = new DBConfiguration("config/resumes.properties");
        SqlStorage storage = new SqlStorage(config.getProperty(PropertyType.DB_URL),
                config.getProperty(PropertyType.DB_USER),
                config.getProperty(PropertyType.DB_PASSWORD));

//        storage.save(new Resume("uuid1", "Vlad"));
//        System.out.println(storage.get("uuid1"));
//        storage.save(new Resume("uuid2", "Geka"));
//        System.out.println(storage.get("uuid2"));
//        storage.save(new Resume("uuid3", "Bill"));
//        System.out.println(storage.get("uuid3"));

//        Resume resume = new Resume("uuid4", "Boris");
//        storage.save(resume);
//        System.out.println(storage.get(resume.getUuid()));
//        storage.delete(resume.getUuid());
//        storage.save(new Resume("uuid5", "Fox"));

//        storage.save(resume);
        storage.clear();
//        for (Resume r : storage.getAllSorted()) {
//            System.out.println(r.getUuid() + "//" + r.getFullName());
//        }

        System.out.println(Arrays.toString(new Resume[]{}));
        System.out.println(Arrays.toString(storage.getAllSorted().toArray()));
        //System.out.println(storage.size());
    }
}

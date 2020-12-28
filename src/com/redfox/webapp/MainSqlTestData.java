package com.redfox.webapp;

import com.redfox.webapp.sql.SqlStorage;
import com.redfox.webapp.sql.properties.DBConfiguration;
import com.redfox.webapp.sql.properties.PropertyType;

public class MainSqlTestData {

    public static void main(String[] args) {
        DBConfiguration config = new DBConfiguration("config/resumes.properties");
        SqlStorage storage = new SqlStorage(config.getProperty(PropertyType.DB_URL),
                config.getProperty(PropertyType.DB_USER),
                config.getProperty(PropertyType.DB_PASSWORD));

//        storage.save(new Resume("uuid1", "Vlad"));
        System.out.println(storage.get("uuid1"));
//        storage.save(new Resume("uuid2", "Geka"));
        System.out.println(storage.get("uuid2"));
//        storage.save(new Resume("uuid3", "Bill"));
        System.out.println(storage.get("uuid3"));


//        storage.delete("uuid1");
//        storage.clear();

        System.out.println(storage.getAllSorted());
        System.out.println(storage.size());
//        storage.clear();
    }
}

package com.redfox.webapp.sql;

import com.redfox.webapp.exception.StorageException;
import com.redfox.webapp.sql.properties.DBConfiguration;
import com.redfox.webapp.sql.properties.PropertyType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {


    public static void main(String[] args) {
        ConnectionFactory connectionFactory = null;
        //
        DBConfiguration config = new DBConfiguration("config/resumes.properties");
        SqlStorage storage = new SqlStorage(config.getProperty(PropertyType.DB_URL),
                config.getProperty(PropertyType.DB_USER),
                config.getProperty(PropertyType.DB_PASSWORD));
        //
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("")) {



            //....
            ps.execute();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

}
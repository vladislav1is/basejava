package com.redfox.webapp.sql;

import com.redfox.webapp.exception.NotExistStorageException;
import com.redfox.webapp.exception.StorageException;
import com.redfox.webapp.model.Resume;
import com.redfox.webapp.storage.Storage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    public final ConnectionFactory connectionFactory;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void save(Resume resume) {
        doStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)", ps -> {
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());
            ps.execute();
        });
    }

    @Override
    public Resume get(String uuid) {
        return doStatement("SELECT * FROM resume WHERE uuid = ?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume resume = new Resume(uuid, rs.getString("full_name"));
            return resume;
        });
    }

    @Override
    public void delete(String uuid) {
        doStatement("DELETE FROM resume WHERE uuid = ?", ps -> {
            ps.setString(1, uuid);
            ps.execute();
        });
    }

    @Override
    public void update(Resume resume) {
        doStatement("UPDATE resume SET full_name = ? WHERE uuid = ?", ps -> {
            ps.setString(1, resume.getFullName());
            ps.setString(2, resume.getUuid());
            ps.execute();
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return doStatement("SELECT * FROM resume ORDER BY full_name", ps -> {
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new StorageException("data is empty");
            }
            List<Resume> resumes = new ArrayList<>();
            while (rs.next()) {
                Resume resume = new Resume(rs.getString("uuid"), rs.getString("full_name"));
                resumes.add(resume);
            }
            return resumes;
        });
    }

    @Override
    public void clear() {
        doStatement("DELETE FROM resume", ps -> {
            ps.execute();
        });
    }

    @Override
    public int size() {
        return doStatement("SELECT count(uuid) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new StorageException("data is empty");
            }
            return rs.getInt(1);
        });
    }

    private interface ConsumerWithSQLExceptions {
        void execute(PreparedStatement ps) throws SQLException;
    }

    private void doStatement(String statement, ConsumerWithSQLExceptions helper) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(statement)) {
            helper.execute(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    private interface SupplierWithSQLExceptions<T> {
        T execute(PreparedStatement ps) throws SQLException;
    }

    private <T> T doStatement(String statement, SupplierWithSQLExceptions<T> helper) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(statement)) {
            return helper.execute(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
    /*
        Вынести общий код (getConnection(), prepareStatement,
        catch SQLException) в класс SqlHelper
        (https://dzone.com/articles/removing-duplicate-code-with-lambda-expressions)
 */
}

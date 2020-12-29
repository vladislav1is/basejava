package com.redfox.webapp.storage;

import com.redfox.webapp.exception.NotExistStorageException;
import com.redfox.webapp.exception.StorageException;
import com.redfox.webapp.model.Resume;
import com.redfox.webapp.sql.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SqlStorage implements Storage {
    private final SqlHelper helper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        helper = new SqlHelper(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void save(Resume resume) {
        helper.doStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)", ps -> {
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());
            ps.execute();
        });
//        TODO:FIX code below!
//        helper.doStatement("SELECT * FROM resume WHERE uuid = ?", ps -> {
//            String uuid = resume.getUuid();
//            ps.setString(1, uuid);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                throw new ExistStorageException(uuid);
//            }
//            helper.doStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)", ps1 -> {
//                ps.setString(1, resume.getUuid());
//                ps.setString(2, resume.getFullName());
//                ps.execute();
//            });
//        });
    }

    @Override
    public Resume get(String uuid) {
        return helper.doStatement("SELECT * FROM resume WHERE uuid = ?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume resume = new Resume(uuid, rs.getString("full_name").trim());
            return resume;
        });
    }

    @Override
    public void delete(String uuid) {
        getExistedKey(uuid);
        helper.doStatement("DELETE FROM resume WHERE uuid = ?", ps -> {
            ps.setString(1, uuid);
            ps.execute();
        });
    }

    @Override
    public void update(Resume resume) {
        getExistedKey(resume.getUuid());
        helper.doStatement("UPDATE resume SET full_name = ? WHERE uuid = ?", ps1 -> {
            ps1.setString(1, resume.getFullName());
            ps1.setString(2, resume.getUuid());
            ps1.execute();
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return helper.doStatement("SELECT * FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            boolean isNext = rs.next();
//            if (!isNext) {
//                throw new StorageException("Data is empty");
//            }
            List<Resume> resumes = new ArrayList<>();
            while (isNext) {
                Resume resume = new Resume(rs.getString("uuid").trim(), rs.getString("full_name").trim());
                resumes.add(resume);
                isNext = rs.next();
            }
            resumes.sort(Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid));
            return resumes;
        });
    }

    @Override
    public void clear() {
        helper.doStatement("DELETE FROM resume", ps -> {
            ps.execute();
        });
    }

    @Override
    public int size() {
        return helper.doStatement("SELECT count(uuid) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new StorageException("data is empty");
            }
            return rs.getInt(1);
        });
    }

    private void getExistedKey(String uuid) {
        helper.doStatement("SELECT * FROM resume WHERE uuid = ?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
        });
    }

    private void getNotExistedKey(String uuid) {
        //...
    }

    private static class SqlHelper {
        public final ConnectionFactory connectionFactory;

        public SqlHelper(String dbUrl, String dbUser, String dbPassword) {
            connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        }

        @FunctionalInterface
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

        @FunctionalInterface
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
    }
}

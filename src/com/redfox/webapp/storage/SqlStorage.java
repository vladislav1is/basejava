package com.redfox.webapp.storage;

import com.redfox.webapp.exception.NotExistStorageException;
import com.redfox.webapp.model.ContactType;
import com.redfox.webapp.model.Resume;
import com.redfox.webapp.sql.SqlHelper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.<Void>transactionalExecute(conn -> {
            String uuid = resume.getUuid();
            sqlHelper.<Void>doPreparedStatement(conn, "INSERT INTO resume (uuid, full_name) VALUES (?,?)", ps -> {
                ps.setString(1, uuid);
                ps.setString(2, resume.getFullName());
                ps.execute();
                return null;
            });
            sqlHelper.<Void>doPreparedStatement(conn, "INSERT INTO contact  (resume_uuid, type, value) VALUES (?,?,?)", ps -> {
                for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                    ps.setString(1, uuid);
                    ps.setString(2, entry.getKey().name());
                    ps.setString(3, entry.getValue());
                    ps.addBatch();
                }
                ps.executeBatch();
                return null;
            });
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.doStatement("" +
                        "    SELECT * FROM resume r " +
                        " LEFT JOIN contact c " +
                        "        ON r.uuid = c.resume_uuid " +
                        "     WHERE r.uuid = ? ",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume resume = new Resume(uuid, rs.getString("full_name"));
                    do {
                        String type = rs.getString("type");
                        if (type != null) {
                            resume.addContact(
                                    ContactType.valueOf(type),
                                    rs.getString("value")
                            );
                        }
                    } while (rs.next());
                    return resume;
                });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.<Void>doStatement("DELETE FROM resume WHERE uuid = ?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.<Void>transactionalExecute(conn -> {
            String uuid = resume.getUuid();
            sqlHelper.<Void>doPreparedStatement(conn, "UPDATE resume SET full_name = ? WHERE uuid = ?", ps -> {
                ps.setString(1, resume.getFullName());
                ps.setString(2, uuid);
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(uuid);
                }
                return null;
            });
            Map<ContactType, String> entryTypes = new EnumMap<>(ContactType.class);
            sqlHelper.<Void>doPreparedStatement(conn, "SELECT * FROM contact WHERE resume_uuid = ?", ps -> {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    entryTypes.put(ContactType.valueOf(rs.getString("type")), rs.getString("value"));
                }
                ps.execute();
                return null;
            });
            sqlHelper.<Void>doPreparedStatement(conn, "UPDATE contact SET value = ? WHERE resume_uuid = ? AND type = ?", ps -> {
                for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                    String keyType = entry.getKey().name();
                    ps.setString(1, entry.getValue());
                    ps.setString(2, uuid);
                    ps.setString(3, keyType);
                    entryTypes.remove(ContactType.valueOf(keyType));
                    ps.addBatch();
                }
                ps.executeBatch();
                return null;
            });
            sqlHelper.<Void>doPreparedStatement(conn, "DELETE FROM contact WHERE resume_uuid = ? AND type = ?", ps -> {
                for (Map.Entry<ContactType, String> entry : entryTypes.entrySet()) {
                    ps.setString(1, uuid);
                    ps.setString(2, entry.getKey().name());
                    ps.addBatch();
                }
                ps.executeBatch();
                return null;
            });
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.transactionalExecute(conn -> {
            List<Resume> resumes = new ArrayList<>();
            sqlHelper.<Void>doPreparedStatement(conn, "SELECT uuid, full_name FROM resume ORDER BY full_name, uuid", ps -> {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Resume resume = new Resume(rs.getString("uuid"), rs.getString("full_name"));
                    resumes.add(resume);
                }
                return null;
            });
            sqlHelper.<Void>doPreparedStatement(conn, "SELECT * FROM contact ORDER BY resume_uuid", ps -> {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    for (Resume r : resumes) {
                        if (rs.getString("resume_uuid").equals(r.getUuid())) {
                            r.addContact(ContactType.valueOf(rs.getString("type")), rs.getString("value"));
                        }
                    }
                }
                return null;
            });
            return resumes;
        });
    }

    @Override
    public void clear() {
        sqlHelper.doStatement("DELETE FROM resume");
    }

    @Override
    public int size() {
        return sqlHelper.doStatement("SELECT count(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }
}

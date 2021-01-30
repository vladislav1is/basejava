package com.redfox.webapp.storage;

import com.redfox.webapp.exception.NotExistStorageException;
import com.redfox.webapp.model.*;
import com.redfox.webapp.sql.SqlHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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
            doInsertContacts(conn, resume);
            doInsertSections(conn, resume);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.doStatement("" +
                        "    SELECT * FROM resume r " +
                        " LEFT JOIN contact c " +
                        "        ON r.uuid = c.resume_uuid " +
                        " LEFT JOIN section s " +
                        "        ON r.uuid = s.resume_uuid " +
                        "     WHERE r.uuid = ? ",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume resume = new Resume(uuid, rs.getString("full_name"));
                    do {
                        doAddContact(rs, resume);
                        doAddSection(rs, resume);
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
            doDeleteContacts(conn, resume);
            doDeleteSections(conn, resume);

            doInsertContacts(conn, resume);
            doInsertSections(conn, resume);
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.transactionalExecute(conn -> {
            Map<String, Resume> resumes = new LinkedHashMap<>();

            sqlHelper.<Void>doPreparedStatement(conn, "SELECT uuid, full_name FROM resume ORDER BY full_name, uuid", ps -> {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    resumes.put(uuid, new Resume(uuid, rs.getString("full_name")));
                }
                return null;
            });
            sqlHelper.<Void>doPreparedStatement(conn, "SELECT * FROM contact", ps -> {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Resume r = resumes.get(rs.getString("resume_uuid"));
                    doAddContact(rs, r);
                }
                return null;
            });
            sqlHelper.<Void>doPreparedStatement(conn, "SELECT * FROM section", ps -> {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Resume r = resumes.get(rs.getString("resume_uuid"));
                    doAddSection(rs, r);
                }
                return null;
            });

            return new ArrayList<>(resumes.values());
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

    private void doInsertContacts(Connection conn, Resume resume) {
        sqlHelper.<Void>doPreparedStatement(conn, "INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)", ps -> {
            for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, entry.getKey().name());
                ps.setString(3, entry.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
            return null;
        });
    }

    private void doInsertSections(Connection conn, Resume resume) {
        sqlHelper.<Void>doPreparedStatement(conn, "INSERT INTO section (resume_uuid, section, content) VALUES (?,?,?)", ps -> {
            for (Map.Entry<SectionType, AbstractSection> entry : resume.getSections().entrySet()) {
                SectionType sectionType = entry.getKey();
                AbstractSection sectionValue = entry.getValue();
                switch (sectionType) {
                    case OBJECTIVE:
                    case PERSONAL:
                        ps.setString(1, resume.getUuid());
                        ps.setString(2, sectionType.name());
                        ps.setString(3, sectionValue.toString());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        ps.setString(1, resume.getUuid());
                        ps.setString(2, sectionType.name());

                        StringBuilder sb = new StringBuilder();
                        List<String> texts = ((ListTextSection) sectionValue).getItems();
                        for (String text : texts) {
                            sb.append(text).append("\n");
                        }
                        ps.setString(3, sb.toString());
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + sectionType);
                }
                ps.execute();
            }
            return null;
        });
    }

    private void doAddContact(ResultSet rs, Resume resume) throws SQLException {
        String type = rs.getString("type");
        if (type != null) {
            resume.addContact(
                    ContactType.valueOf(type),
                    rs.getString("value")
            );
        }
    }

    private void doAddSection(ResultSet rs, Resume resume) throws SQLException {
        String type = rs.getString("section");
        if (type != null) {
            AbstractSection sectionContent;
            SectionType sectionType = SectionType.valueOf(type);
            switch (sectionType) {
                case OBJECTIVE:
                case PERSONAL:
                    sectionContent = new TextSection(rs.getString("content"));
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    sectionContent = new ListTextSection(rs.getString("content").split("\n"));
                    break;
                default:
                    throw new IllegalStateException("Unexpected content: " + sectionType);
            }
            resume.addSection(
                    SectionType.valueOf(type),
                    sectionContent
            );
        }
    }

    private void doDeleteContacts(Connection conn, Resume resume) {
        sqlHelper.<Void>doPreparedStatement(conn, "DELETE FROM contact WHERE resume_uuid = ?", ps -> {
            ps.setString(1, resume.getUuid());
            ps.execute();
            return null;
        });
    }

    private void doDeleteSections(Connection conn, Resume resume) {
        sqlHelper.<Void>doPreparedStatement(conn, "DELETE FROM section WHERE resume_uuid = ?", ps -> {
            ps.setString(1, resume.getUuid());
            ps.execute();
            return null;
        });
    }
}

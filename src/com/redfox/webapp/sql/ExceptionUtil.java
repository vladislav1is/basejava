package com.redfox.webapp.sql;

import com.redfox.webapp.exception.ExistStorageException;
import com.redfox.webapp.exception.StorageException;
import org.postgresql.util.PSQLException;

import java.sql.SQLException;

public class ExceptionUtil {
    private ExceptionUtil() {
    }

    public static StorageException convertException(SQLException exception) {
        if (exception instanceof PSQLException) {
//            http://www.postgresql.org/docs/9.3/static/errcodes-appendix.html
            if (exception.getSQLState().equals("23505")) {
                return new ExistStorageException(null);
            }
        }
        return new StorageException(exception);
    }
}
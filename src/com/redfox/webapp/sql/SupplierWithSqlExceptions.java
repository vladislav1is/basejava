package com.redfox.webapp.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface SupplierWithSqlExceptions<T> {
    T execute(PreparedStatement ps) throws SQLException;
}

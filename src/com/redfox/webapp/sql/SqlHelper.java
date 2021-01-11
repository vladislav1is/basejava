package com.redfox.webapp.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {

    public final ConnectionFactory connectionFactory;

    public SqlHelper(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public void doStatement(String sql) {
        doStatement(sql, PreparedStatement::execute);
    }

    public  <T> T doStatement(String sql, SupplierWithSqlExceptions<T> supplier) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            return supplier.execute(ps);
        } catch (SQLException e) {
            throw ExceptionUtil.convertException(e);
        }
    }

    @FunctionalInterface
    public interface SupplierWithSqlExceptions<T> {
        T execute(PreparedStatement ps) throws SQLException;
    }
}
package com.eugen.onlineshop.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcConnector {
    private final String jdbcUrl;
    private final String jdbcLogin;
    private final String jdbcPassword;

    public JdbcConnector(Properties properties) throws JdbcDataException {
        jdbcUrl = properties.getProperty("DB_URL");
        jdbcLogin = properties.getProperty("DB_LOGIN");
        jdbcPassword = properties.getProperty("DB_PASSWORD");

        if (jdbcUrl == null) {
            throw new JdbcDataException("DB_URL is null");
        }
        if (jdbcLogin == null) {
            throw new JdbcDataException("DB_LOGIN is null");
        }
        if (jdbcPassword == null) {
            throw new JdbcDataException("DB_PASSWORD is null");
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcUrl, jdbcLogin, jdbcPassword);
    }
}

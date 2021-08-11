package com.eugen.onlineshop.jdbc;

public class JdbcDataException extends RuntimeException {
    public JdbcDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public JdbcDataException(String message) {
        super(message);
    }
}

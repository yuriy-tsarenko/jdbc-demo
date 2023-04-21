package com.goit.examples.connection.mysql;

import com.goit.examples.connection.exception.DatabaseException;
import com.mysql.cj.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Database {

    private static final String MYSQL_URL = "jdbc:mysql://localhost:3306";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "12345";
    private final Connection connection;

    private Database() throws SQLException {
        DriverManager.registerDriver(new Driver());
        connection = DriverManager.getConnection(MYSQL_URL, USERNAME, PASSWORD);
    }

    public static Database getInstance() {
        try {
            return new Database();
        } catch (Exception e) {
            throw new DatabaseException("Instantiation od Database failed", e);
        }
    }

    public Connection getConnection() {
        return connection;
    }
}

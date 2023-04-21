package com.goit.examples.connection.h2;

import com.goit.examples.connection.exception.DatabaseException;
import org.h2.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Database {

    private static final String H2_URL = "jdbc:h2:mem:local";
    private final Connection connection;

    private Database() throws SQLException {
        DriverManager.registerDriver(new Driver());
        connection = DriverManager.getConnection(H2_URL);
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

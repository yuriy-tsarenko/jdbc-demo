package com.goit.dabase;

import com.mysql.cj.jdbc.Driver;

import java.sql.*;
import java.util.Objects;
import java.util.function.Function;

public class Datasource {
    private final String url;
    private final String username;
    private final String password;
    private Connection connection;
    private Statement statement;

    public Datasource(String url, String username, String password) throws SQLException {
        this.url = url;
        this.username = username;
        this.password = password;
        DriverManager.registerDriver(new Driver());
    }

    public void openConnection(String url, String username, String password) throws SQLException {
        connection = DriverManager.getConnection(url, username, password);
        statement = connection.createStatement();
    }

    public void close() {
        try {
            if (Objects.nonNull(connection) && !connection.isClosed()) {
                statement.close();
                connection.close();
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public <T> T dbCall(Function<Statement, T> action) {
        T result = null;
        try {
            if (Objects.isNull(connection) || connection.isClosed()) {
                openConnection(url, username, password);
            }
            result = action.apply(statement);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return result;
    }


}

package com.goit.examples.reource.write.to.db.h2;

import com.goit.examples.connection.h2.Database;
import com.goit.examples.reource.exception.DbInitException;
import com.goit.examples.reource.reading.Reader;

import java.sql.Connection;
import java.sql.Statement;

public class DbInitializer {
    private final Database database;
    private final Reader reader;

    public DbInitializer(Database database, Reader reader) {
        this.database = database;
        this.reader = reader;
    }

    public void init() {
        String sql = reader.read();
        try (Connection connection = database.getConnection();
             Statement statement = connection.createStatement()) {
            String[] queries = sql.split(";");
            statement.execute("CREATE SCHEMA IF NOT EXISTS user_manager_db;");
            statement.execute("USE user_manager_db;");
            for (String query : queries) {
                statement.execute(query);
            }
        } catch (Exception e) {
            throw new DbInitException("Db init failed", e);
        }
    }
}

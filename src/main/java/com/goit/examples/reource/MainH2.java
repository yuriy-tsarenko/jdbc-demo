package com.goit.examples.reource;

import com.goit.examples.connection.h2.Database;
import com.goit.examples.reource.reading.Reader;
import com.goit.examples.reource.write.to.db.h2.DbInitializer;

public class MainH2 {
    public static void main(String[] args) {
        Reader reader = new Reader("db/db-structure/init-db.sql");
        Database database = Database.getInstance();
        DbInitializer dbInitializer = new DbInitializer(database, reader);
        dbInitializer.init("user_manager_db");
    }
}

package com.goit.examples.reource;

import com.goit.examples.connection.mysql.Database;
import com.goit.examples.reource.reading.Reader;
import com.goit.examples.reource.write.to.db.mysql.DbInitializer;

public class MainMysql {
    public static void main(String[] args) {
        Reader reader = new Reader("db/db-structure/employee_db.sql");
        Database database = Database.getInstance();
        DbInitializer dbInitializer = new DbInitializer(database, reader);
        dbInitializer.init("employee_db");
    }
}

package com.goit.db.client;

import com.goit.db.client.dabase.Datasource;
import com.goit.db.client.dabase.Repository;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    private static final String DB_URL = "jdbc:mysql://localhost:3306";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "12345";

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select database:");
        String databaseName = scanner.nextLine();
        Datasource datasource = new Datasource(DB_URL, USERNAME, PASSWORD);
        datasource.setDatabaseName(databaseName);
        Repository repository = new Repository(datasource);
        while (true) {
            System.out.println("Type query:");
            String query = scanner.nextLine();
            System.out.println(repository.execute(query));
        }
    }
}

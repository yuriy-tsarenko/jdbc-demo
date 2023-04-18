package com.goit;

import com.goit.dabase.Datasource;
import com.goit.dabase.Repository;

import java.sql.SQLException;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    private static final String DB_URL = "jdbc:mysql://localhost:3306";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "12345";

    public static void main(String[] args) throws SQLException {
        String userChoice = "Y";
        Datasource datasource = new Datasource(DB_URL, USERNAME, PASSWORD);
        Repository repository = new Repository(datasource);
        Scanner scanner = new Scanner(System.in);
        while (Objects.equals(userChoice, "Y")) {
            System.out.println("type 'DML' or 'DDL'");
            String queryType = scanner.nextLine();
            System.out.println("Type query:");
            String query = scanner.nextLine();
            switch (queryType) {
                case "DML":
                    System.out.println(repository.select(query));
                    break;
                case "DDL":
                    System.out.println(repository.create(query));
                    break;
                default:
                    System.err.println(queryType + " not supported");
            }

            System.out.println("type 'Y' to continue or 'N' to exit");
            userChoice = scanner.nextLine();
        }
    }
}

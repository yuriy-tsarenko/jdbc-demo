package com.goit.db.client.dabase;

import com.goit.db.client.enums.ReturnType;
import com.goit.db.client.enums.QueryType;
import lombok.RequiredArgsConstructor;
import org.intellij.lang.annotations.Language;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.goit.db.client.converter.RowsConverter.convertToString;
import static com.goit.db.client.enums.QueryType.DDL;
import static com.goit.db.client.enums.QueryType.DML;
import static com.goit.db.client.enums.ReturnType.NUMERIC;
import static com.goit.db.client.enums.ReturnType.RESULT_SET;
import static com.goit.db.client.enums.ReturnType.VOID;

@RequiredArgsConstructor
public class Repository {

    private final Datasource datasource;

    public String execute(String query) {
        QueryType queryType = resolveQueryType(query);
        switch (queryType) {
            case DML:
                return executeDML(query);
            case DDL:
                return executeVoid(query);
            default:
                String message = queryType + " not supported";
                System.err.println(message);
                throw new RuntimeException(message);
        }
    }

    private String executeDML(@Language("SQL") String query) {
        ReturnType returnType = resolveReturnType(query);
        switch (returnType) {
            case RESULT_SET:
                return select(query);
            case NUMERIC:
                return executeUpdate(query);
            case VOID:
                return executeVoid(query);
            default:
                String message = returnType + " not supported";
                System.err.println(message);
                throw new RuntimeException(message);
        }
    }

    private String select(@Language("SQL") String query) {
        return datasource.dbCall(statement -> {
            try {
                ResultSet resultSet = statement.executeQuery(query);
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();
                List<Map<String, Object>> rows = new LinkedList<>();
                while (resultSet.next()) {
                    Map<String, Object> row = new HashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnName(i);
                        Object value = resultSet.getObject(columnName);
                        row.put(columnName, value);
                    }
                    rows.add(row);
                }
                return convertToString(rows);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private String executeUpdate(String query) {
        return datasource.dbCall(statement -> {
            try {
                return String.valueOf(statement.executeUpdate(query));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }


    private String executeVoid(@Language("SQL") String query) {
        return datasource.dbCall(statement -> {
            try {
                boolean execute = statement.execute(query);
                return execute
                        ? "query executed successfully"
                        : "query failed";
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }


    //DML - SELECT, INSERT, UPDATE, DELETE
    //DDL - CREATE, ALTER
    private static QueryType resolveQueryType(String query) {
        String inputQueryType = getInputQueryType(query);
        return switch (inputQueryType.toUpperCase()) {
            case "SELECT", "INSERT", "UPDATE", "CALL", "DELETE", "TRUNCATE" -> DML;
            default -> DDL;
        };
    }

    private static ReturnType resolveReturnType(String query) {
        String inputQueryType = getInputQueryType(query);
        return switch (inputQueryType.toUpperCase()) {
            case "SELECT" -> RESULT_SET;
            case "UPDATE" -> NUMERIC;
            default -> VOID;
        };
    }

    private static String getInputQueryType(String query) {
        return query.split(" ")[0];
    }
}

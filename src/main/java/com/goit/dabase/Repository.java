package com.goit.dabase;

import lombok.RequiredArgsConstructor;
import org.intellij.lang.annotations.Language;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.goit.converter.RowsConverter.convertToString;

@RequiredArgsConstructor
public class Repository {

    private final Datasource datasource;

    public String select(@Language("SQL") String query) {
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


    public String create(@Language("SQL") String query) {
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
}
